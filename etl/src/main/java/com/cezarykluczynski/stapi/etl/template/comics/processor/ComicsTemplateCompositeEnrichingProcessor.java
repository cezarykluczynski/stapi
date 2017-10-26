package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ComicsTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, ComicsTemplate>> {

	private static final String CREATORS = "Creators";
	private static final String CREDITS = "Credits";

	private final PageSectionExtractor pageSectionExtractor;

	private final ComicsTemplateWikitextStaffEnrichingProcessor comicsTemplateWikitextStaffEnrichingProcessor;

	public ComicsTemplateCompositeEnrichingProcessor(PageSectionExtractor pageSectionExtractor,
			ComicsTemplateWikitextStaffEnrichingProcessor comicsTemplateWikitextStaffEnrichingProcessor) {
		this.pageSectionExtractor = pageSectionExtractor;
		this.comicsTemplateWikitextStaffEnrichingProcessor = comicsTemplateWikitextStaffEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, ComicsTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		ComicsTemplate comicsTemplate = enrichablePair.getOutput();
		List<PageSection> staffPageSectionList = pageSectionExtractor.findByTitles(page, CREATORS, CREDITS);

		if (!staffPageSectionList.isEmpty()) {
			comicsTemplateWikitextStaffEnrichingProcessor.enrich(EnrichablePair.of(staffPageSectionList.get(0).getWikitext(), comicsTemplate));
		}
	}

}
