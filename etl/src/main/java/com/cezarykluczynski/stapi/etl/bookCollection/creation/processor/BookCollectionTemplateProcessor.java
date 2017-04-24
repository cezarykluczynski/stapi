package com.cezarykluczynski.stapi.etl.bookCollection.creation.processor;

import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate;
import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookCollectionTemplateProcessor implements ItemProcessor<BookCollectionTemplate, BookCollection> {

	private final GuidGenerator guidGenerator;

	@Inject
	public BookCollectionTemplateProcessor(GuidGenerator guidGenerator) {
		this.guidGenerator = guidGenerator;
	}

	@Override
	public BookCollection process(BookCollectionTemplate item) throws Exception {
		BookCollection bookCollection = new BookCollection();

		bookCollection.setTitle(item.getTitle());
		bookCollection.setPage(item.getPage());
		bookCollection.setGuid(guidGenerator.generateFromPage(item.getPage(), BookCollection.class));
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
