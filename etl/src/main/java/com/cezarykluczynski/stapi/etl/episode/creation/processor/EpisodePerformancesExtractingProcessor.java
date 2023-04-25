package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.enums.PerformanceType;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.AS_END_LINE;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.AS_SEPARATOR;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.DASH_END_LINE;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.FOR_END_LINE;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.FOR_SEPARATOR;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.HYPEN_END_LINE;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.LINKS_AND_REFERENCES;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.NDASH_END_LINE;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.PERFORMANCES_SECTION;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.SECTIONS_TO_PERFORMANCE_TYPE_MAP;
import static com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesMediaSections.SKIPPABLE_PAGES;

@Service
@Slf4j
@RequiredArgsConstructor
public class EpisodePerformancesExtractingProcessor implements ItemProcessor<Page, List<EpisodePerformanceDTO>> {

	private final WikitextApi wikitextApi;

	private final PageApi pageApi;

	@Override
	@NonNull
	public List<EpisodePerformanceDTO> process(Page page) {
		List<EpisodePerformanceDTO> episodePerformances = Lists.newArrayList();
		List<PageSection> pageSectionList = page.getSections();

		Optional<PageSection> pageSectionOptional = findSubsectionsOfSectionWithTitle(pageSectionList, LINKS_AND_REFERENCES);

		if (!pageSectionOptional.isPresent()) {
			log.warn("Section {} not found in episode \"{}\".", LINKS_AND_REFERENCES, page.getTitle());
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
			if (!StringUtils.defaultIfBlank(number, StringUtils.EMPTY).startsWith(linksAndReferencesNumber) || !PERFORMANCES_SECTION.contains(text)) {
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
		List<String> lines = getLines(wikitext, performanceType);

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
			List<String> parts = Lists.newArrayList();
			if (hasAsSeparatorAndIsPerformanceType) {
				parts = Arrays.stream(StringUtils.splitByWholeSeparatorPreserveAllTokens(line, AS_SEPARATOR)).collect(Collectors.toList());
			}
			if (hasForSeparator) {
				parts = Arrays.stream(StringUtils.splitByWholeSeparatorPreserveAllTokens(line, FOR_SEPARATOR)).collect(Collectors.toList());
			}

			boolean noSeparators = !hasAsSeparator && !hasForSeparator;
			if (noSeparators) {
				parts = Lists.newArrayList(line);
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
									if (pageLinkList.size() == 1 && noSeparators) {
										EpisodePerformanceDTO episodePerformanceDTONext = new EpisodePerformanceDTO();
										episodePerformanceDTONext.setPerformanceType(performanceType);
										episodePerformanceDTONext.setPerformerName(pageLinkList.get(j).getTitle());
										performances.add(episodePerformanceDTONext);
										log.info("Only one link for line \"{}\", creating performer duplicate.", linePart);
									}
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
									if (pageLinkList.size() == 1 && noSeparators) {
										EpisodePerformanceDTO episodePerformanceDTONext = new EpisodePerformanceDTO();
										episodePerformanceDTONext.setPerformanceType(performanceType);
										episodePerformanceDTONext.setPerformerName(pageLinkList.get(j).getTitle());
										performances.add(episodePerformanceDTONext);
										log.info("Only one link for line \"{}\", creating stand-in/stunt duplicate.", linePart);
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
				if (!performanceShouldBeFilteredOut(performance, performances, episodePerformances)) {
					episodePerformances.add(performance);
				}
			}
		}
		return episodePerformances;
	}

	private boolean performanceShouldBeFilteredOut(EpisodePerformanceDTO performance, List<EpisodePerformanceDTO> performances,
			List<EpisodePerformanceDTO> episodePerformances) {
		final PerformanceType performanceTypeLocal = performance.getPerformanceType();
		final String performerName = performance.getPerformerName();
		final String characterName = performance.getCharacterName();
		final String performingFor = performance.getPerformingFor();
		if (performerName != null || characterName != null || performingFor != null) {
			if (PerformanceType.STAND_IN.equals(performanceTypeLocal) || PerformanceType.STUNT.equals(performanceTypeLocal)) {
				if (characterName == null && performingFor == null) {
					final List<EpisodePerformanceDTO> allOtherPerformances = performances.stream()
							.filter(other -> other != performance) // that's on purpose, we're excluding `performance` here
							.filter(other -> other.getPerformerName().equals(performerName))
							.collect(Collectors.toList());
					if (!allOtherPerformances.isEmpty()) {
						final List<EpisodePerformanceDTO> performancesWithFor = allOtherPerformances.stream()
								.filter(other -> other.getPerformingFor() != null)
								.collect(Collectors.toList());
						if (!performancesWithFor.isEmpty()) {
							return true;
						}
						if (episodePerformances.stream().anyMatch(other -> other.equals(performance))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private List<String> getLines(String wikitext, PerformanceType performanceType) {
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
		return lines;
	}

	private Optional<PageSection> findSubsectionsOfSectionWithTitle(List<PageSection> pageSections, String sectionTitle) {
		return pageSections
				.stream()
				.filter(pageSection -> sectionTitle.equals(pageSection.getText()))
				.findFirst();
	}

}
