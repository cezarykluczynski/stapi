package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.RawDatelinkExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.etl.template.episode.provider.EpisodeFinalScriptDateFixedValueProvider;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Service
@Slf4j
public class EpisodeTemplateDatesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Page, EpisodeTemplate>> {

	private RawDatelinkExtractingProcessor rawDatelinkExtractingProcessor;

	private EpisodeFinalScriptDateFixedValueProvider episodeFinalScriptDateFixedValueProvider;

	@Inject
	public EpisodeTemplateDatesEnrichingProcessor(RawDatelinkExtractingProcessor rawDatelinkExtractingProcessor,
			EpisodeFinalScriptDateFixedValueProvider episodeFinalScriptDateFixedValueProvider) {
		this.rawDatelinkExtractingProcessor = rawDatelinkExtractingProcessor;
		this.episodeFinalScriptDateFixedValueProvider = episodeFinalScriptDateFixedValueProvider;
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

		FixedValueHolder<LocalDate> fixedValueHolder = episodeFinalScriptDateFixedValueProvider
				.getSearchedValue(episodeTitle);

		if (fixedValueHolder.isFound()) {
			episodeTemplate.setFinalScriptDate(fixedValueHolder.getValue());
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
