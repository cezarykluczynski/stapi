package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.enums.PerformanceType;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EpisodePerformancesExtractingProcessor implements ItemProcessor<Page, List<EpisodePerformanceDTO>> {

	private static final String LINKS_AND_REFERENCES = "Links and references";
	private static final String STARRING = "Starring";
	private static final String ALSO_STARRING = "Also starring";
	private static final String GUEST_STARS = "Guest stars";
	private static final String SPECIAL_GUEST_STARS = "Special guest stars";
	private static final String CO_STARS = "Co-stars";
	private static final String UNCREDITED_CO_STARS = "Uncredited co-stars";
	private static final String STUNT_DOUBLE = "Stunt double";
	private static final String STAND_INS = "Stand-ins";
	private static final String REFERENCES = "References";
	private static final String OTHER_REFERENCES = "Other references";
	private static final String UNREFERENCED_MATERIAL = "Unreferenced material";
	private static final String TIMELINE = "Timeline";
	private static final String EXTERNAL_LINKS = "External links";
	private static final String AS_SEPARATOR = " as ";
	private static final String FOR_SEPARATOR = " for ";

	private static final List<String> PEFORMANCES_SECTION = Lists.newArrayList(
			STARRING,
			ALSO_STARRING,
			GUEST_STARS,
			SPECIAL_GUEST_STARS,
			CO_STARS,
			UNCREDITED_CO_STARS,
			STUNT_DOUBLE,
			STAND_INS
	);

	private static final Map<String, PerformanceType> SECTIONS_TO_PERFORMANCE_TYPE_MAP = Maps.newHashMap();

	static {
		SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(STARRING, PerformanceType.PERFORMANCE);
		SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(ALSO_STARRING, PerformanceType.PERFORMANCE);
		SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(GUEST_STARS, PerformanceType.PERFORMANCE);
		SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(SPECIAL_GUEST_STARS, PerformanceType.PERFORMANCE);
		SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(CO_STARS, PerformanceType.PERFORMANCE);
		SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(UNCREDITED_CO_STARS, PerformanceType.PERFORMANCE);
		SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(STUNT_DOUBLE, PerformanceType.STUNT);
		SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(STAND_INS, PerformanceType.STAND_IN);
	}

	private static final List<String> IGNORABLE_SECTIONS = Lists.newArrayList(
			REFERENCES,
			OTHER_REFERENCES,
			UNREFERENCED_MATERIAL,
			TIMELINE,
			EXTERNAL_LINKS
	);

	private static final List<String> SKIPPABLE_PAGES = Lists.newArrayList(
			"lieutenant",
			"counselor",
			"doctor",
			"lieutenant commander",
			"commander",
			"captain",
			"stunt double",
			"stand-in",
			"ensign",
			"photo double",
			"crewman"
	);

	private static final List<String> INVALID_PAGE_TITLES = Lists.newArrayList(
			"cat"
	);

	private WikitextApi wikitextApi;

	public EpisodePerformancesExtractingProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

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
		String linksAndReferencesNumber = linksAndReferencesPageSection.getNumber();

		preValidateSectionNames(pageSectionList, linksAndReferencesNumber);

		episodePerformances.addAll(getPerformances(pageSectionList, linksAndReferencesNumber));

		return episodePerformances;
	}

	private List<EpisodePerformanceDTO> getPerformances(List<PageSection> pageSectionList, String linksAndReferencesNumber) {
		List<EpisodePerformanceDTO> episodePerformances = Lists.newArrayList();
		for (PageSection pageSection : pageSectionList) {
			String number = pageSection.getNumber();
			String text = pageSection.getText();
			String wikitext = pageSection.getWikitext();
			if (!StringUtils.defaultIfBlank(number, StringUtils.EMPTY).startsWith(linksAndReferencesNumber) || !PEFORMANCES_SECTION.contains(text)) {
				continue;
			}

			episodePerformances.addAll(getPerformancesFromWikitext(wikitext, SECTIONS_TO_PERFORMANCE_TYPE_MAP.get(text)));
		}

		return episodePerformances;
	}

	private List<EpisodePerformanceDTO> getPerformancesFromWikitext(String wikitext, PerformanceType performanceType) {
		List<EpisodePerformanceDTO> episodePerformances = Lists.newArrayList();

		List<String> lines = Lists.newArrayList(wikitext.split("\n"));

		for (String line : lines) {
			if (!line.startsWith("*")) {
				continue;
			}

			List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(line)
					.stream()
					.filter(pageLink -> !SKIPPABLE_PAGES.contains(pageLink.getTitle().toLowerCase()))
					.collect(Collectors.toList());

			List<String> pageTitlesLowercase = pageLinkList.stream()
					.map(PageLink::getTitle)
					.map(String::toLowerCase)
					.collect(Collectors.toList());

			if (pageTitlesLowercase.stream()
					.anyMatch(pageTitleToLowercase -> pageTitleToLowercase.startsWith("unnamed ") || pageTitleToLowercase.startsWith("unknown "))) {
				continue;
			}

			List<String> invalidPageTitles = pageTitlesLowercase
					.stream()
					.filter(INVALID_PAGE_TITLES::contains)
					.collect(Collectors.toList());

			if (!invalidPageTitles.isEmpty()) {
				continue;
			}

			if (pageLinkList.size() >= 2 && (wikitext.contains(AS_SEPARATOR) || wikitext.contains(FOR_SEPARATOR))) {
				List<PageLink> roles = pageLinkList.subList(1, pageLinkList.size());
				for (PageLink role : roles) {
					EpisodePerformanceDTO episodePerformanceDTO = new EpisodePerformanceDTO();
					episodePerformanceDTO.setPerformerName(pageLinkList.get(0).getTitle());
					if (PerformanceType.PERFORMANCE.equals(performanceType)) {
						episodePerformanceDTO.setCharacterName(role.getTitle());
					} else {
						episodePerformanceDTO.setPerformingFor(role.getTitle());
					}
					episodePerformanceDTO.setPerformanceType(performanceType);
					episodePerformances.add(episodePerformanceDTO);
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

	private void preValidateSectionNames(List<PageSection> pageSections, String mainSectionNumber) {
		pageSections
				.stream()
				.filter(pageSection -> {
					String text = pageSection.getText();
					return !PEFORMANCES_SECTION.contains(text) && !IGNORABLE_SECTIONS.contains(text) && text.startsWith(mainSectionNumber);
				}).forEach(pageSection -> log.error("Unknown section {} in parent section {}",
						pageSection.getText(), LINKS_AND_REFERENCES));
	}

}
