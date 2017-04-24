package com.cezarykluczynski.stapi.etl.template.book.processor.collection;

import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookTemplateToBookCollectionTemplateProcessor implements ItemProcessor<BookTemplate, BookCollectionTemplate> {

	@Override
	public BookCollectionTemplate process(BookTemplate item) throws Exception {
		BookCollectionTemplate bookCollectionTemplate = new BookCollectionTemplate();

		bookCollectionTemplate.setPage(item.getPage());
		bookCollectionTemplate.setTitle(item.getTitle());
		bookCollectionTemplate.setPublishedYear(item.getPublishedYear());
		bookCollectionTemplate.setPublishedMonth(item.getPublishedMonth());
		bookCollectionTemplate.setPublishedDay(item.getPublishedDay());
		bookCollectionTemplate.setNumberOfPages(item.getNumberOfPages());
		bookCollectionTemplate.setStardateFrom(item.getStardateFrom());
		bookCollectionTemplate.setStardateTo(item.getStardateTo());
		bookCollectionTemplate.setYearFrom(item.getYearFrom());
		bookCollectionTemplate.setYearTo(item.getYearTo());
		bookCollectionTemplate.getBookSeries().addAll(item.getBookSeries());
		bookCollectionTemplate.getAuthors().addAll(item.getAuthors());
		bookCollectionTemplate.getArtists().addAll(item.getArtists());
		bookCollectionTemplate.getEditors().addAll(item.getEditors());
		bookCollectionTemplate.getPublishers().addAll(item.getPublishers());
		bookCollectionTemplate.getCharacters().addAll(item.getCharacters());
		bookCollectionTemplate.getReferences().addAll(item.getReferences());

		return bookCollectionTemplate;
	}

}
