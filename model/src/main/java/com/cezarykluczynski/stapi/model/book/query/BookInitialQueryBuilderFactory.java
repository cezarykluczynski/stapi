package com.cezarykluczynski.stapi.model.book.query;

import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book.entity.Book_;
import com.cezarykluczynski.stapi.model.common.query.InitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookInitialQueryBuilderFactory implements InitialQueryBuilderFactory<BookRequestDTO, Book> {

	private final BookQueryBuilderFactory bookQueryBuilderFactory;

	public BookInitialQueryBuilderFactory(BookQueryBuilderFactory bookQueryBuilderFactory) {
		this.bookQueryBuilderFactory = bookQueryBuilderFactory;
	}

	@Override
	public QueryBuilder<Book> createInitialQueryBuilder(BookRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Book> bookQueryBuilder = bookQueryBuilderFactory.createQueryBuilder(pageable);

		bookQueryBuilder.equal(Book_.uid, criteria.getUid());
		bookQueryBuilder.like(Book_.title, criteria.getTitle());
		bookQueryBuilder.between(Book_.publishedYear, criteria.getPublishedYearFrom(), criteria.getPublishedYearTo());
		bookQueryBuilder.between(Book_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		bookQueryBuilder.between(Book_.yearFrom, criteria.getYearFrom(), null);
		bookQueryBuilder.between(Book_.yearTo, null, criteria.getYearTo());
		bookQueryBuilder.between(Book_.audiobookPublishedYear, criteria.getAudiobookPublishedYearFrom(), criteria.getAudiobookPublishedYearTo());
		bookQueryBuilder.between(Book_.audiobookRunTime, criteria.getAudiobookRunTimeFrom(), criteria.getAudiobookRunTimeTo());
		bookQueryBuilder.between(Book_.stardateFrom, criteria.getStardateFrom(), null);
		bookQueryBuilder.between(Book_.stardateTo, null, criteria.getStardateTo());
		bookQueryBuilder.equal(Book_.novel, criteria.getNovel());
		bookQueryBuilder.equal(Book_.referenceBook, criteria.getReferenceBook());
		bookQueryBuilder.equal(Book_.biographyBook, criteria.getBiographyBook());
		bookQueryBuilder.equal(Book_.rolePlayingBook, criteria.getRolePlayingBook());
		bookQueryBuilder.equal(Book_.eBook, criteria.getEBook());
		bookQueryBuilder.equal(Book_.anthology, criteria.getAnthology());
		bookQueryBuilder.equal(Book_.novelization, criteria.getNovelization());
		bookQueryBuilder.equal(Book_.audiobook, criteria.getAudiobook());
		bookQueryBuilder.equal(Book_.audiobookAbridged, criteria.getAudiobookAbridged());
		bookQueryBuilder.setSort(criteria.getSort());

		return bookQueryBuilder;
	}
}
