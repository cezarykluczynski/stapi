package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.enums.PerformanceType;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EpisodePerformancesExtractingProcessor implements ItemProcessor<Page, List<EpisodePerformanceDTO>> {

	private static final String LINKS_AND_REFERENCES = "Links and references";
	private static final String AS_SEPARATOR = " as ";
	private static final String AS_END_LINE = " as";
	private static final String FOR_SEPARATOR = " for ";
	private static final String FOR_END_LINE = " for";
	private static final String DASH_END_LINE = " –";
	private static final String HYPEN_END_LINE = " -";
	private static final String NDASH_END_LINE = " &ndash;";
	private static final String OPEN_WIKITEXT_LINK = "[[";

	private static final List<String> STARRING = Lists.newArrayList(
			"Guest stars",
			"Uncredited guest cast",
			"Starring the voices of",
			"Special Guest Stars",
			"Also starring",
			"Guest stars 2",
			"Featuring",
			"Special appearance by 2",
			"Also Starring",
			"Cast reappearances",
			"Special appearance by",
			"Co-stars",
			"Starring 2",
			"Special guest appearance by",
			"Cast and characters",
			"Actors appearing in the original Star Trek episode",
			"Special guest star",
			"Performers",
			"Uncredited",
			"Guest starring",
			"Co-starring",
			"Main cast",
			"Also starring the voices of",
			"Uncredited Co-Stars",
			"Starring",
			"Special guest stars in alphabetical order",
			"Also Starring 2",
			"CGI co-stars",
			"Guest star",
			"Additional characters",
			"Special Guest Star",
			"Special Apearance By",
			"Special Appearance By",
			"Co-Stars",
			"Guest Star",
			"Co-star",
			"Uncredited co-star",
			"Special guest stars",
			"Background Characters",
			"And guest starring",
			"Guest cast",
			"Uncredited co-stars appearing in the original Star Trek episode",
			"Cast",
			"And Special Guest Star",
			"Co-Star",
			"And special guest star",
			"Also starring 2",
			"Remastered extras",
			"Uncredited cast",
			"Characters",
			"Uncredited co-stars appearing in the original Star Trek episode \"The Cage\"",
			"Special guest appearances",
			"Uncredited archive footage appearances",
			"Special Guest Appearance by",
			"Guest Stars",
			"Uncredited Co-Star",
			"Special Guest Appearance By",
			"Uncredited co-stars",
			"Background characters",
			"Guest background characters",
			"Uncredited Co-stars"
	);

	private static final List<String> STUNTS = Lists.newArrayList(
			"Stunt doubles",
			"Stunts",
			"Stunt double",
			// Duplicated section, decision was made to make it part of stunts
			"Stunt Doubles and Stand-ins",
			"Stunt doubles and stand-in",
			"Stunt doubles appearing in the original Star Trek episode",
			"Stunt doubles and stunt crew",
			"Stunt and body doubles",
			"Stunt Doubles",
			"Stunt Double",
			"Stunt performers",
			"Doubles"
	);

	private static final List<String> STAND_INS = Lists.newArrayList(
			"Stand-ins and photo double",
			"Photo doubles",
			"Photo double",
			"Photo Double",
			"Stand-in",
			"Stand-ins",
			"Stand-ins and photo doubles",
			// Duplicated section, decision was made to make it part of stunts
			// "Stunt Doubles and Stand-ins",
			// "Stunt doubles and stand-in",
			"Stand-ins/Doubles",
			"Stand-ins and doubles",
			"Stand-ins and stunt doubles",
			"Stands-ins"
	);

	private static final List<String> PEFORMANCES_SECTION = Lists.newArrayList();
	private static final Map<String, PerformanceType> SECTIONS_TO_PERFORMANCE_TYPE_MAP = Maps.newHashMap();

	static {
		PEFORMANCES_SECTION.addAll(STARRING);
		PEFORMANCES_SECTION.addAll(STUNTS);
		PEFORMANCES_SECTION.addAll(STAND_INS);
		for (String section : STARRING) {
			SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(section, PerformanceType.PERFORMANCE);
		}
		for (String section : STUNTS) {
			SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(section, PerformanceType.STUNT);
		}
		for (String section : STAND_INS) {
			SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(section, PerformanceType.STAND_IN);
		}
	}

	private static final List<String> SKIPPABLE_PAGES = Lists.newArrayList(
			"lt.", "lieutenant",
			"counselor",
			"doctor", "dr.",
			"lieutenant commander", "lt. cmdr.",
			"commander", "cmdr.",
			"captain", "capt.",
			"stunt double",
			"stand-in",
			"ensign",
			"chief",
			"constable",
			"photo double", "hand double",
			"crewman",
			"lieutenant junior grade", "lieutenant jg",
			"colonel",
			"gul",
			"admiral",
			"major",
			"kai"
	);

	private final WikitextApi wikitextApi;

	private final PageApi pageApi;

	@Override
	public List<EpisodePerformanceDTO> process(Page page) {
		List<EpisodePerformanceDTO> episodePerformances = Lists.newArrayList();
		List<PageSection> pageSectionList = page.getSections();

		Optional<PageSection> pageSectionOptional = findSubsectionsOfSectionWithTitle(pageSectionList, LINKS_AND_REFERENCES);

		if (!pageSectionOptional.isPresent()) {
			log.warn("Section {} not found in episode {}", LINKS_AND_REFERENCES, page.getTitle());
			return episodePerformances;
		}

		PageSection linksAndReferencesPageSection = pageSectionOptional.get();
		String wikitext = linksAndReferencesPageSection.getWikitext();
		if (wikitext != null && wikitext.contains("{{")) {
			final List<String> templateNamesFromWikitext = wikitextApi.getTemplateNamesFromWikitext(wikitext);
			for (String templateName : templateNamesFromWikitext) {
				final Page template = pageApi.getTemplate(templateName, page.getMediaWikiSource());
				if (template != null) {
					final List<PageSection> templateSections = template.getSections();
					AtomicReference<PageSection> lastReplacedSection = new AtomicReference<>();
					for (PageSection templateSection : templateSections) {
						pageSectionList.stream()
								.filter(pageSection -> StringUtils.equals(pageSection.getAnchor(), templateSection.getAnchor()))
								.findFirst()
								.ifPresent(pageSection -> {
									pageSection.setWikitext(templateSection.getWikitext());
									lastReplacedSection.set(pageSection);
								});
					}
					final PageSection pageSection = lastReplacedSection.get();
					if (pageSection != null) {
						pageSection.setWikitext(pageSection.getWikitext() + wikitext);
					}
				}
			}
		}
		String linksAndReferencesNumber = linksAndReferencesPageSection.getNumber();
		episodePerformances.addAll(getPerformances(page, pageSectionList, linksAndReferencesNumber));
		return episodePerformances;
	}

	private List<EpisodePerformanceDTO> getPerformances(Page page, List<PageSection> pageSectionList, String linksAndReferencesNumber) {
		List<EpisodePerformanceDTO> episodePerformances = Lists.newArrayList();
		for (PageSection pageSection : pageSectionList) {
			String number = pageSection.getNumber();
			String text = pageSection.getText();
			String wikitext = pageSection.getWikitext();
			if (!StringUtils.defaultIfBlank(number, StringUtils.EMPTY).startsWith(linksAndReferencesNumber) || !PEFORMANCES_SECTION.contains(text)) {
				continue;
			}

			episodePerformances.addAll(getPerformancesFromWikitext(page, wikitext, SECTIONS_TO_PERFORMANCE_TYPE_MAP.get(text), episodePerformances));
		}

		return episodePerformances;
	}

	@SuppressWarnings({"CyclomaticComplexity", "NPathComplexity", "MethodLength", "ModifiedControlVariable", "BooleanExpressionComplexity"})
	private List<EpisodePerformanceDTO> getPerformancesFromWikitext(Page page, String wikitext, PerformanceType performanceType,
																	List<EpisodePerformanceDTO> foundSoFar) {
		List<EpisodePerformanceDTO> episodePerformances = Lists.newArrayList();
		List<String> lines = Lists.newArrayList(wikitext.split("\n"));

		List<Integer> indexesToMerge = Lists.newArrayList();
		for (int i = lines.size(); i-- > 1;) { // 1, we still need something to merge to
			String line = lines.get(i);
			if (line != null && StringUtils.trim(line).startsWith("**")) {
				indexesToMerge.add(i);
			}
		}

		for (Integer indexToMerge : indexesToMerge) {
			final String line = lines.get(indexToMerge);
			int previousIndex = indexToMerge - 1;
			String separator = PerformanceType.PERFORMANCE.equals(performanceType) ? AS_SEPARATOR : FOR_SEPARATOR;
			lines.set(previousIndex, lines.get(previousIndex) + separator + line);
			lines.remove((int) indexToMerge);
		}

		for (String line : lines) {
			if (!line.startsWith("*")) {
				continue;
			}

			final ArrayList<String> lineEndingsToReplace = Lists.newArrayList(NDASH_END_LINE, DASH_END_LINE, HYPEN_END_LINE);
			for (String lineEndingToReplace : lineEndingsToReplace) {
				if (line.endsWith(lineEndingToReplace)) {
					line = StringUtils.replace(line, lineEndingToReplace,
							PerformanceType.PERFORMANCE.equals(performanceType) ? AS_END_LINE : FOR_END_LINE);
					break;
				}
			}

			boolean hasAsSeparator = line.contains(AS_SEPARATOR) || line.endsWith(AS_END_LINE);
			boolean hasForSeparator = line.contains(FOR_SEPARATOR) || line.endsWith(FOR_END_LINE);

			boolean hasAsSeparatorAndIsPerformanceType = hasAsSeparator && PerformanceType.PERFORMANCE.equals(performanceType);
			if (!hasAsSeparator && !hasForSeparator) {
				// common pattern for performer with no role, don't log those
				if (!((line.startsWith("* [[") || line.startsWith("*[["))
						&& (line.endsWith("]]") || line.endsWith("]] {{small|(photograph only)}}")
							|| line.endsWith("]] {{small|(skeletal remains)}}") || line.endsWith("]] {{small|(photo)}}")
							|| line.endsWith("]] {{small|(photograph)}}"))
						&& StringUtils.lastIndexOf(line, OPEN_WIKITEXT_LINK) == StringUtils.indexOf(line, OPEN_WIKITEXT_LINK))) {
					log.info("Line \"{}\" from page {} has no known separator, skipping.", line, page.getTitle());
				}
				continue;
			}
			List<String> parts = Lists.newArrayList();
			if (hasAsSeparatorAndIsPerformanceType) {
				parts = Arrays.stream(StringUtils.splitByWholeSeparatorPreserveAllTokens(line, AS_SEPARATOR)).collect(Collectors.toList());
			}
			if (hasForSeparator) {
				parts = Arrays.stream(StringUtils.splitByWholeSeparatorPreserveAllTokens(line, FOR_SEPARATOR)).collect(Collectors.toList());
			}

			if (parts.size() < 2) {
				log.info("Line \"{}\" from page {} could not be separated into two distinct parts, skipping.", line, page.getTitle());
			}

			EpisodePerformanceDTO episodePerformanceDTO = null;
			List<EpisodePerformanceDTO> performances = Lists.newArrayList();
			for (int i = 0; i < parts.size(); i++) {
				boolean isPerformer = i == 0;
				boolean isPerformance = i > 0;
				boolean isNextPerformance = i > 1;
				Optional<PerformanceType> performanceTypeFromText = Optional.empty();
				if (isPerformance) {
					String previousPart = parts.get(i - 1);
					if (previousPart.length() > 20) {
						previousPart = previousPart.substring(previousPart.length() - 20);
					}
					if (PerformanceType.STUNT.equals(performanceType)
							&& (StringUtils.containsIgnoreCase(previousPart, "stand"))) {
						performanceTypeFromText = Optional.of(PerformanceType.STAND_IN);
					} else if (PerformanceType.STAND_IN.equals(performanceType) && StringUtils.containsIgnoreCase(previousPart, "stunt")) {
						performanceTypeFromText = Optional.of(PerformanceType.STUNT);
					}
				}
				if (isPerformer) {
					episodePerformanceDTO = new EpisodePerformanceDTO();
					episodePerformanceDTO.setPerformanceType(performanceType);
					performances.add(episodePerformanceDTO);
				}
				if (isNextPerformance) {
					episodePerformanceDTO = episodePerformanceDTO.copyOf();
					episodePerformanceDTO.setPerformanceType(performanceType);
					episodePerformanceDTO.setCharacterName(null);
					episodePerformanceDTO.setPerformingFor(null);
					performances.add(episodePerformanceDTO);
				}
				String linePart = parts.get(i);
				List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(linePart)
						.stream()
						.filter(pageLink -> !SKIPPABLE_PAGES.contains(pageLink.getTitle().toLowerCase()))
						.collect(Collectors.toList());

				List<String> pageTitlesLowercase = pageLinkList.stream()
						.map(PageLink::getTitle)
						.map(String::toLowerCase)
						.collect(Collectors.toList());

				if (pageTitlesLowercase.stream().anyMatch(pageTitleToLowercase -> pageTitleToLowercase.startsWith("unnamed ")
						|| pageTitleToLowercase.startsWith("unknown "))) {
					if (isPerformer) {
						break;
					} else {
						continue;
					}
				}

				if (pageLinkList.size() == 0) {
					AtomicBoolean performerNameMatchesForSeparator = new AtomicBoolean();
					AtomicBoolean performingForMatchesForSeparator = new AtomicBoolean();
					List<EpisodePerformanceDTO> foundSoFarWithLatest = Lists.newArrayList();
					foundSoFarWithLatest.addAll(foundSoFar);
					foundSoFarWithLatest.addAll(episodePerformances);
					final Optional<EpisodePerformanceDTO> previousMatchOptional = foundSoFarWithLatest.stream()
							.filter(previousEpisodePerformanceDTO -> {
								if (!isPerformance) {
									return StringUtils.containsIgnoreCase(linePart, previousEpisodePerformanceDTO.getPerformerName())
											|| StringUtils.containsIgnoreCase(previousEpisodePerformanceDTO.getPerformerName(), linePart);
								} else if (hasAsSeparatorAndIsPerformanceType) {
									return StringUtils.containsIgnoreCase(linePart, previousEpisodePerformanceDTO.getCharacterName())
											|| StringUtils.containsIgnoreCase(previousEpisodePerformanceDTO.getCharacterName(), linePart);
								} else if (hasForSeparator) {
									performingForMatchesForSeparator.set(
											StringUtils.containsIgnoreCase(linePart, previousEpisodePerformanceDTO.getPerformingFor())
											|| StringUtils.containsIgnoreCase(previousEpisodePerformanceDTO.getPerformingFor(), linePart));
									performerNameMatchesForSeparator.set(
											StringUtils.containsIgnoreCase(linePart, previousEpisodePerformanceDTO.getPerformerName())
											|| StringUtils.containsIgnoreCase(previousEpisodePerformanceDTO.getPerformerName(), linePart));
									return performingForMatchesForSeparator.get() || performerNameMatchesForSeparator.get();
								}
								return false;
							})
							.findFirst();
					if (previousMatchOptional.isPresent()) {
						final EpisodePerformanceDTO previousEpisodePerformanceDTO = previousMatchOptional.get();
						if (!isPerformance && previousEpisodePerformanceDTO.getPerformerName() != null) {
							episodePerformanceDTO.setPerformerName(previousEpisodePerformanceDTO.getPerformerName());
						} else if (hasAsSeparatorAndIsPerformanceType && previousEpisodePerformanceDTO.getCharacterName() != null) {
							episodePerformanceDTO.setCharacterName(previousEpisodePerformanceDTO.getCharacterName());
						} else if (hasForSeparator) {
							if (PerformanceType.PERFORMANCE.equals(previousEpisodePerformanceDTO.getPerformanceType())
								&& previousEpisodePerformanceDTO.getPerformerName() != null && performerNameMatchesForSeparator.get()) {
								episodePerformanceDTO.setPerformingFor(previousEpisodePerformanceDTO.getPerformerName());
							} else if (previousEpisodePerformanceDTO.getPerformingFor() != null && performingForMatchesForSeparator.get()) {
								episodePerformanceDTO.setPerformingFor(previousEpisodePerformanceDTO.getPerformingFor());
							}
							if (performanceTypeFromText.isPresent()) {
								episodePerformanceDTO.setPerformanceType(performanceTypeFromText.get());
							}
						}
					}
				} else {
					if (!isPerformance) {
						episodePerformanceDTO.setPerformerName(pageLinkList.get(0).getTitle());
					} else {
						for (int j = 0; j < pageLinkList.size(); j++) {
							if (PerformanceType.PERFORMANCE.equals(performanceType)) {
								if (j == 0) {
									episodePerformanceDTO.setCharacterName(pageLinkList.get(j).getTitle());
								} else {
									EpisodePerformanceDTO episodePerformanceDTONext = episodePerformanceDTO.copyOf();
									episodePerformanceDTONext.setCharacterName(pageLinkList.get(j).getTitle());
									performances.add(episodePerformanceDTONext);
								}
							} else {
								if (j == 0) {
									episodePerformanceDTO.setPerformingFor(pageLinkList.get(j).getTitle());
									if (performanceTypeFromText.isPresent()) {
										episodePerformanceDTO.setPerformanceType(performanceTypeFromText.get());
									}
								} else {
									EpisodePerformanceDTO episodePerformanceDTONext = episodePerformanceDTO.copyOf();
									episodePerformanceDTONext.setPerformingFor(pageLinkList.get(j).getTitle());
									performanceTypeFromText.ifPresent(episodePerformanceDTONext::setPerformanceType);
									performances.add(episodePerformanceDTONext);
								}
							}
						}
					}
				}
			}
			for (EpisodePerformanceDTO performance : performances) {
				if (performance.getPerformerName() != null
						&& (performance.getCharacterName() != null || performance.getPerformingFor() != null)) {
					episodePerformances.add(performance);
				}
			}
		}
		return episodePerformances;
	}

	private Optional<PageSection> findSubsectionsOfSectionWithTitle(List<PageSection> pageSections, String sectionTitle) {
		return pageSections
				.stream()
				.filter(pageSection -> sectionTitle.equals(pageSection.getText()))
				.findFirst();
	}

}
