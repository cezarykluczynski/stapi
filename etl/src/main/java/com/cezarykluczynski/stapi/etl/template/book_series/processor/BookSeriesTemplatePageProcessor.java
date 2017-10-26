package com.cezarykluczynski.stapi.etl.template.book_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookSeriesTemplatePageProcessor implements ItemProcessor<Page, BookSeriesTemplate> {

	private final PageBindingService pageBindingService;

	private final TemplateFinder templateFinder;

	private final BookSeriesTemplatePartsEnrichingProcessor bookSeriesTemplatePartsEnrichingProcessor;

	private final BookSeriesTemplateFixedValuesEnrichingProcessor bookSeriesTemplateFixedValuesEnrichingProcessor;

	private final BookSeriesTemplateEBookSeriesProcessor bookSeriesTemplateEBookSeriesProcessor;

	public BookSeriesTemplatePageProcessor(PageBindingService pageBindingService, TemplateFinder templateFinder,
			BookSeriesTemplatePartsEnrichingProcessor bookSeriesTemplatePartsEnrichingProcessor,
			BookSeriesTemplateFixedValuesEnrichingProcessor bookSeriesTemplateFixedValuesEnrichingProcessor,
			BookSeriesTemplateEBookSeriesProcessor bookSeriesTemplateEBookSeriesProcessor) {
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.bookSeriesTemplatePartsEnrichingProcessor = bookSeriesTemplatePartsEnrichingProcessor;
		this.bookSeriesTemplateFixedValuesEnrichingProcessor = bookSeriesTemplateFixedValuesEnrichingProcessor;
		this.bookSeriesTemplateEBookSeriesProcessor = bookSeriesTemplateEBookSeriesProcessor;
	}

	@Override
	public BookSeriesTemplate process(Page item) throws Exception {
		if (!item.getRedirectPath().isEmpty()) {
			return null;
		}

		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate();
		bookSeriesTemplate.setTitle(TitleUtil.getNameFromTitle(item.getTitle()));
		bookSeriesTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		bookSeriesTemplate.setEBookSeries(bookSeriesTemplateEBookSeriesProcessor.process(item));

		bookSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(bookSeriesTemplate, bookSeriesTemplate));

		Optional<Template> sidebarBookSeriesTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_NOVEL_SERIES);
		if (!sidebarBookSeriesTemplateOptional.isPresent()) {
			return bookSeriesTemplate;
		}

		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(sidebarBookSeriesTemplateOptional.get().getParts(), bookSeriesTemplate));

		return bookSeriesTemplate;
	}

}
