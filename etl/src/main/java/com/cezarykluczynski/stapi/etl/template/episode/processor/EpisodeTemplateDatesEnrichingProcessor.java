package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.RawDatelinkExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Service
@Slf4j
public class EpisodeTemplateDatesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Page, EpisodeTemplate>> {

	private static final Map<String, LocalDate> FIXED_DATES = Maps.newHashMap();

	static {
		FIXED_DATES.put("Elaan of Troyius", LocalDate.of(1968, 5, 27));
		FIXED_DATES.put("Beyond the Farthest Star", LocalDate.of(1973, 5, 10));
		FIXED_DATES.put("The Lorelei Signal", LocalDate.of(1973, 6, 5));
		FIXED_DATES.put("Ex Post Facto", LocalDate.of(1994, 12, 15));
		FIXED_DATES.put("Proving Ground", LocalDate.of(2003, 10, 28));
		FIXED_DATES.put("Dagger of the Mind", LocalDate.of(1966, 8, 8));
		FIXED_DATES.put("The Menagerie, Part II", LocalDate.of(1966, 10, 7));
		FIXED_DATES.put("That Which Survives", LocalDate.of(1968, 9, 16));
		FIXED_DATES.put("What Are Little Girls Made Of?", LocalDate.of(1966, 7, 27));
		FIXED_DATES.put("Albatross", LocalDate.of(1974, 6, 27));
		FIXED_DATES.put("Bem", null);
		FIXED_DATES.put("The Practical Joker", LocalDate.of(1974, 5, 6));
		FIXED_DATES.put("True Q", LocalDate.of(1992, 8, 26));
		FIXED_DATES.put("In the Cards", LocalDate.of(1997, 3, 21));
		FIXED_DATES.put("Lifesigns", LocalDate.of(1995, 12, 4));
	}

	private DayMonthYearProcessor dayMonthYearProcessor;

	private RawDatelinkExtractingProcessor rawDatelinkExtractingProcessor;

	@Inject
	public EpisodeTemplateDatesEnrichingProcessor(DayMonthYearProcessor dayMonthYearProcessor,
			RawDatelinkExtractingProcessor rawDatelinkExtractingProcessor) {
		this.dayMonthYearProcessor = dayMonthYearProcessor;
		this.rawDatelinkExtractingProcessor = rawDatelinkExtractingProcessor;
	}


	private static final List<String> RELEVANT_PAGE_SECTIONS_NAMES = newArrayList(
			"Background information",
			"Title, story, and script",
			"Story",
			"Production",
			"Story and production",
			"Title, story, script, and production",
			"Story, script and cast"
	);

	private static final List<String> FINAL_SCRIPT_DATES_KEYWORDS = newArrayList(
			"final script draft",
			"final draft",
			"revised draft"
	);

	@Override
	public void enrich(EnrichablePair<Page, EpisodeTemplate> enrichablePair) {
		EpisodeTemplate episodeTemplate = enrichablePair.getOutput();
		String episodeTitle = episodeTemplate.getTitle();

		if (FIXED_DATES.containsKey(episodeTitle)) {
			episodeTemplate.setFinalScriptDate(FIXED_DATES.get(episodeTitle));
			return;
		}

		List<PageSection> pageSectionList = getDateRelevantPageSectionList(enrichablePair.getInput());
		List<String> sectionsListItems = getSectionsListItems(pageSectionList);

		sectionsListItems.forEach(sectionsListItem -> {
			if (isFinalDraftScriptSection(sectionsListItem)) {
				trySetFinalScriptDate(sectionsListItem, enrichablePair.getOutput());
			}
		});
	}

	private boolean isFinalDraftScriptSection(String section) {section = section.toLowerCase();
		return FINAL_SCRIPT_DATES_KEYWORDS.stream()
				.anyMatch(section::contains);
	}

	private void trySetFinalScriptDate(String sectionsListItem, EpisodeTemplate episodeTemplate) {
		List<LocalDate> localDateList = Lists.newArrayList();
		try {
			localDateList = rawDatelinkExtractingProcessor.process(sectionsListItem);
		} catch (Exception e) {
		}

		if (localDateList.size() == 1) {
			episodeTemplate.setFinalScriptDate(localDateList.get(0));
		} else if (localDateList.size() > 1) {
			log.warn("Could not extract final script date for episode \"{}\" from string \"{}\", " +
					"found multiple dates: {}", episodeTemplate.getTitle(), sectionsListItem, localDateList);
		}
	}

	private List<PageSection> getDateRelevantPageSectionList(Page page) {
		return page.getSections()
				.stream()
				.filter(this::isRelevantPageSection)
				.collect(Collectors.toList());
	}

	private List<String> getSectionsListItems(List<PageSection> pageSectionList) {
		List<String> sectionsListItems = newArrayList();

		pageSectionList.forEach(pageSection ->
			sectionsListItems.addAll(Lists.newArrayList(pageSection.getWikitext().split("\n")).stream()
					.filter(line -> line.startsWith("*"))
					.collect(Collectors.toList())));

		return sectionsListItems;
	}

	private boolean isRelevantPageSection(PageSection pageSection) {
		return RELEVANT_PAGE_SECTIONS_NAMES.contains(pageSection.getText());
	}

}
