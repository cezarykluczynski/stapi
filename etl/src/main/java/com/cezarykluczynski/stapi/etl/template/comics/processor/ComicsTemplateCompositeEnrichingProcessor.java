package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComicsTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, ComicsTemplate>> {

	private static final String CREATORS = "Creators";
	private static final String CREDITS = "Credits";
	private static final String CHARACTERS = "Characters";

	private PageSectionExtractor pageSectionExtractor;

	private ComicsTemplateWikitextStaffEnrichingProcessor comicsTemplateWikitextStaffEnrichingProcessor;

	private ComicsTemplateWikitextCharactersEnrichingProcessor comicsTemplateWikitextCharactersEnrichingProcessor;

	@Inject
	public ComicsTemplateCompositeEnrichingProcessor(PageSectionExtractor pageSectionExtractor,
			ComicsTemplateWikitextStaffEnrichingProcessor comicsTemplateWikitextStaffEnrichingProcessor,
			ComicsTemplateWikitextCharactersEnrichingProcessor comicsTemplateWikitextCharactersEnrichingProcessor) {
		this.pageSectionExtractor = pageSectionExtractor;
		this.comicsTemplateWikitextStaffEnrichingProcessor = comicsTemplateWikitextStaffEnrichingProcessor;
		this.comicsTemplateWikitextCharactersEnrichingProcessor = comicsTemplateWikitextCharactersEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, ComicsTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		ComicsTemplate comicsTemplate = enrichablePair.getOutput();
		List<PageSection> staffPageSectionList = pageSectionExtractor.findByTitles(page, CREATORS, CREDITS);
		List<PageSection> charactersPageSectionList = pageSectionExtractor.findByTitlesIncludingSubsections(page, CHARACTERS);

		if (!staffPageSectionList.isEmpty()) {
			comicsTemplateWikitextStaffEnrichingProcessor.enrich(EnrichablePair.of(staffPageSectionList.get(0).getWikitext(), comicsTemplate));
		}

		if (!charactersPageSectionList.isEmpty()) {
			comicsTemplateWikitextCharactersEnrichingProcessor
					.enrich(EnrichablePair.of(joinSectionsWikitext(charactersPageSectionList), comicsTemplate));
		}
	}

	private String joinSectionsWikitext(List<PageSection> charactersPageSectionList) {
		return String.join("\n", charactersPageSectionList.stream().map(PageSection::getWikitext).collect(Collectors.toList()));
	}

}
