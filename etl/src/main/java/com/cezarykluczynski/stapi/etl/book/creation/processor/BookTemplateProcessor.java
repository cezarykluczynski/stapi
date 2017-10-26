package com.cezarykluczynski.stapi.etl.book.creation.processor;

import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookTemplateProcessor implements ItemProcessor<BookTemplate, Book> {

	private final UidGenerator uidGenerator;

	public BookTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public Book process(BookTemplate item) throws Exception {
		Book book = new Book();

		book.setTitle(item.getTitle());
		book.setPage(item.getPage());
		book.setUid(uidGenerator.generateFromPage(item.getPage(), Book.class));
		book.setPublishedYear(item.getPublishedYear());
		book.setPublishedMonth(item.getPublishedMonth());
		book.setPublishedDay(item.getPublishedDay());
		book.setNumberOfPages(item.getNumberOfPages());
		book.setStardateFrom(item.getStardateFrom());
		book.setStardateTo(item.getStardateTo());
		book.setYearFrom(item.getYearFrom());
		book.setYearTo(item.getYearTo());
		book.setNovel(item.getNovel());
		book.setReferenceBook(item.getReferenceBook());
		book.setBiographyBook(item.getBiographyBook());
		book.setRolePlayingBook(item.getRolePlayingBook());
		book.setEBook(item.getEBook());
		book.setAnthology(item.getAnthology());
		book.setNovelization(item.getNovelization());
		book.setAudiobook(item.getAudiobook());
		book.setAudiobookAbridged(item.getAudiobookAbridged());
		book.setAudiobookPublishedYear(item.getAudiobookPublishedYear());
		book.setAudiobookPublishedMonth(item.getAudiobookPublishedMonth());
		book.setAudiobookPublishedDay(item.getAudiobookPublishedDay());
		book.setAudiobookRunTime(item.getAudiobookRunTime());
		book.setProductionNumber(item.getProductionNumber());
		book.getBookSeries().addAll(item.getBookSeries());
		book.getAuthors().addAll(item.getAuthors());
		book.getArtists().addAll(item.getArtists());
		book.getEditors().addAll(item.getEditors());
		book.getAudiobookNarrators().addAll(item.getAudiobookNarrators());
		book.getPublishers().addAll(item.getPublishers());
		book.getAudiobookPublishers().addAll(item.getAudiobookPublishers());
		book.getCharacters().addAll(item.getCharacters());
		book.getReferences().addAll(item.getReferences());
		book.getAudiobookReferences().addAll(item.getAudiobookReferences());

		return book;

	}

}
