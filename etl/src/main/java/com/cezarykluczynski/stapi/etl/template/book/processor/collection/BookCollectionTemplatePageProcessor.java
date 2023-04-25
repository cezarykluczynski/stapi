package com.cezarykluczynski.stapi.etl.template.book.processor.collection;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.book.processor.BookTemplatePageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCollectionTemplatePageProcessor implements ItemProcessor<Page, BookCollectionTemplate> {

	private final BookCollectionPageFilter bookCollectionPageFilter;

	private final BookTemplatePageProcessor bookTemplatePageProcessor;

	private final BookTemplateToBookCollectionTemplateProcessor bookTemplateToComicCollectionTemplateProcessor;

	private final BookCollectionTemplateWikitextBooksProcessor bookCollectionTemplateWikitextBooksProcessor;


	@Override
	public BookCollectionTemplate process(Page item) throws Exception {
		if (bookCollectionPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

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
