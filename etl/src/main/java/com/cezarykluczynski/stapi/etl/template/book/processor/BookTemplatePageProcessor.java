package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.book.creation.service.BookPageFilter;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class BookTemplatePageProcessor implements ItemProcessor<Page, BookTemplate> {

	private final BookPageFilter bookPageFilter;

	private final PageBindingService pageBindingService;

	private final TemplateFinder templateFinder;

	private final CategoriesBookTemplateEnrichingProcessor categoriesBookTemplateEnrichingProcessor;

	private final BookTemplatePartsEnrichingProcessor bookTemplatePartsEnrichingProcessor;

	@Inject
	public BookTemplatePageProcessor(BookPageFilter bookPageFilter, PageBindingService pageBindingService, TemplateFinder templateFinder,
			CategoriesBookTemplateEnrichingProcessor categoriesBookTemplateEnrichingProcessor,
			BookTemplatePartsEnrichingProcessor bookTemplatePartsEnrichingProcessor) {
		this.bookPageFilter = bookPageFilter;
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.categoriesBookTemplateEnrichingProcessor = categoriesBookTemplateEnrichingProcessor;
		this.bookTemplatePartsEnrichingProcessor = bookTemplatePartsEnrichingProcessor;
	}

	@Override
	public BookTemplate process(Page item) throws Exception {
		if (bookPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		BookTemplate bookTemplate = new BookTemplate();
		bookTemplate.setTitle(item.getTitle());
		bookTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		categoriesBookTemplateEnrichingProcessor.enrich(EnrichablePair.of(item.getCategories(), bookTemplate));

		Optional<Template> sidebarBookTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_NOVEL,
				TemplateTitle.SIDEBAR_REFERENCE_BOOK, TemplateTitle.SIDEBAR_RPG_BOOK, TemplateTitle.SIDEBAR_BIOGRAPHY_BOOK);

		if (sidebarBookTemplateOptional.isPresent()) {
			bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(sidebarBookTemplateOptional.get().getParts(), bookTemplate));
		}

		return bookTemplate;
	}

}
