package com.cezarykluczynski.stapi.etl.book_collection.creation.processor;

import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionTemplateProcessor implements ItemProcessor<BookCollectionTemplate, BookCollection> {

	private final UidGenerator uidGenerator;

	public BookCollectionTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public BookCollection process(BookCollectionTemplate item) throws Exception {
		BookCollection bookCollection = new BookCollection();

		bookCollection.setTitle(item.getTitle());
		bookCollection.setPage(item.getPage());
		bookCollection.setUid(uidGenerator.generateFromPage(item.getPage(), BookCollection.class));
		bookCollection.setPublishedYear(item.getPublishedYear());
		bookCollection.setPublishedMonth(item.getPublishedMonth());
		bookCollection.setPublishedDay(item.getPublishedDay());
		bookCollection.setNumberOfPages(item.getNumberOfPages());
		bookCollection.setYearFrom(item.getYearFrom());
		bookCollection.setYearTo(item.getYearTo());
		bookCollection.getBookSeries().addAll(item.getBookSeries());
		bookCollection.getAuthors().addAll(item.getAuthors());
		bookCollection.getArtists().addAll(item.getArtists());
		bookCollection.getEditors().addAll(item.getEditors());
		bookCollection.getPublishers().addAll(item.getPublishers());
		bookCollection.getCharacters().addAll(item.getCharacters());
		bookCollection.getReferences().addAll(item.getReferences());
		bookCollection.getBooks().addAll(item.getBooks());

		return bookCollection;
	}
}
