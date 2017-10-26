package com.cezarykluczynski.stapi.etl.template.book.processor.collection;

import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.book.processor.BookTemplatePageProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionTemplatePageProcessor implements ItemProcessor<Page, BookCollectionTemplate> {

	private final BookTemplatePageProcessor bookTemplatePageProcessor;

	private final BookTemplateToBookCollectionTemplateProcessor bookTemplateToComicCollectionTemplateProcessor;

	private final BookCollectionTemplateWikitextBooksProcessor bookCollectionTemplateWikitextBooksProcessor;

	public BookCollectionTemplatePageProcessor(BookTemplatePageProcessor bookTemplatePageProcessor,
			BookTemplateToBookCollectionTemplateProcessor bookTemplateToComicCollectionTemplateProcessor,
			BookCollectionTemplateWikitextBooksProcessor bookCollectionTemplateWikitextBooksProcessor) {
		this.bookTemplatePageProcessor = bookTemplatePageProcessor;
		this.bookTemplateToComicCollectionTemplateProcessor = bookTemplateToComicCollectionTemplateProcessor;
		this.bookCollectionTemplateWikitextBooksProcessor = bookCollectionTemplateWikitextBooksProcessor;
	}

	@Override
	public BookCollectionTemplate process(Page item) throws Exception {
		BookTemplate bookTemplate = bookTemplatePageProcessor.process(item);

		if (bookTemplate == null) {
			return null;
		}

		BookCollectionTemplate bookCollectionTemplate = bookTemplateToComicCollectionTemplateProcessor.process(bookTemplate);
		bookCollectionTemplate.getBooks().addAll(bookCollectionTemplateWikitextBooksProcessor.process(item));
		bookCollectionTemplate.getBooks().forEach(book -> bookCollectionTemplate.getCharacters().addAll(book.getCharacters()));

		return bookCollectionTemplate;
	}

}
