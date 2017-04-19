package com.cezarykluczynski.stapi.etl.template.bookSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.bookSeries.dto.BookSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class BookSeriesTemplatePageProcessor implements ItemProcessor<Page, BookSeriesTemplate> {

	private PageBindingService pageBindingService;

	private TemplateFinder templateFinder;

	private BookSeriesTemplatePartsEnrichingProcessor bookSeriesTemplatePartsEnrichingProcessor;

	private BookSeriesTemplateFixedValuesEnrichingProcessor bookSeriesTemplateFixedValuesEnrichingProcessor;

	@Inject
	public BookSeriesTemplatePageProcessor(PageBindingService pageBindingService, TemplateFinder templateFinder,
			BookSeriesTemplatePartsEnrichingProcessor bookSeriesTemplatePartsEnrichingProcessor,
			BookSeriesTemplateFixedValuesEnrichingProcessor bookSeriesTemplateFixedValuesEnrichingProcessor) {
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.bookSeriesTemplatePartsEnrichingProcessor = bookSeriesTemplatePartsEnrichingProcessor;
		this.bookSeriesTemplateFixedValuesEnrichingProcessor = bookSeriesTemplateFixedValuesEnrichingProcessor;
	}

	@Override
	public BookSeriesTemplate process(Page item) throws Exception {
		if (!item.getRedirectPath().isEmpty()) {
			return null;
		}

		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate();
		bookSeriesTemplate.setTitle(item.getTitle());
		bookSeriesTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		bookSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(bookSeriesTemplate, bookSeriesTemplate));

		Optional<Template> sidebarBookSeriesTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_COMIC_SERIES);
		if (!sidebarBookSeriesTemplateOptional.isPresent()) {
			return bookSeriesTemplate;
		}

		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(sidebarBookSeriesTemplateOptional.get().getParts(), bookSeriesTemplate));

		return bookSeriesTemplate;
	}

}
