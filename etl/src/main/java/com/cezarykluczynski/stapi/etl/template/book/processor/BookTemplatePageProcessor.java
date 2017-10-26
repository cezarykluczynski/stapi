package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.book.creation.service.BookPageFilter;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.character.WikitextSectionsCharactersProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookTemplatePageProcessor implements ItemProcessor<Page, BookTemplate> {

	private static final List<String> TITLE_PART_LIST_TO_CLEAR = Lists.newArrayList("(songbook)", "(novel)", "(New Frontier novel)", "(game)",
			"(VOY novel)", "(program)", "(eBook)", "(TNG novel)", "(book)", "(young adult novelization)", "(reference book)", "(TOS novel)",
			"(Decipher)", "(Last Unicorn)", "(FASA)", "(Harlan Ellison Collection)", "(omnibus)");

	private final BookPageFilter bookPageFilter;

	private final PageBindingService pageBindingService;

	private final TemplateFinder templateFinder;

	private final CategoriesBookTemplateEnrichingProcessor categoriesBookTemplateEnrichingProcessor;

	private final BookTemplatePartsEnrichingProcessor bookTemplatePartsEnrichingProcessor;

	private final WikitextSectionsCharactersProcessor wikitextSectionsCharactersProcessor;

	public BookTemplatePageProcessor(BookPageFilter bookPageFilter, PageBindingService pageBindingService, TemplateFinder templateFinder,
			CategoriesBookTemplateEnrichingProcessor categoriesBookTemplateEnrichingProcessor,
			BookTemplatePartsEnrichingProcessor bookTemplatePartsEnrichingProcessor,
			WikitextSectionsCharactersProcessor wikitextSectionsCharactersProcessor) {
		this.bookPageFilter = bookPageFilter;
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.categoriesBookTemplateEnrichingProcessor = categoriesBookTemplateEnrichingProcessor;
		this.bookTemplatePartsEnrichingProcessor = bookTemplatePartsEnrichingProcessor;
		this.wikitextSectionsCharactersProcessor = wikitextSectionsCharactersProcessor;
	}

	@Override
	public BookTemplate process(Page item) throws Exception {
		if (bookPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		BookTemplate bookTemplate = new BookTemplate();
		bookTemplate.setTitle(maybeClearTitle(item.getTitle()));
		bookTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		categoriesBookTemplateEnrichingProcessor.enrich(EnrichablePair.of(item.getCategories(), bookTemplate));
		bookTemplate.getCharacters().addAll(wikitextSectionsCharactersProcessor.process(item));

		Optional<Template> sidebarBookTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_NOVEL,
				TemplateTitle.SIDEBAR_REFERENCE_BOOK, TemplateTitle.SIDEBAR_RPG_BOOK, TemplateTitle.SIDEBAR_BIOGRAPHY_BOOK,
				TemplateTitle.SIDEBAR_AUDIO);

		if (sidebarBookTemplateOptional.isPresent()) {
			bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(sidebarBookTemplateOptional.get().getParts(), bookTemplate));
		}

		return bookTemplate;
	}

	private String maybeClearTitle(String title) {
		return StringUtils.trim(StringUtil.containsAnyIgnoreCase(title, TITLE_PART_LIST_TO_CLEAR) ? TitleUtil.getNameFromTitle(title) : title);
	}

}
