package com.cezarykluczynski.stapi.model.book_series.repository;

import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries_;
import com.cezarykluczynski.stapi.model.book_series.query.BookSeriesQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BookSeriesRepositoryImpl implements BookSeriesRepositoryCustom {

	private final BookSeriesQueryBuilderFactory bookSeriesQueryBuilderFactory;

	public BookSeriesRepositoryImpl(BookSeriesQueryBuilderFactory bookSeriesQueryBuilderFactory) {
		this.bookSeriesQueryBuilderFactory = bookSeriesQueryBuilderFactory;
	}

	@Override
	public Page<BookSeries> findMatching(BookSeriesRequestDTO criteria, Pageable pageable) {
		QueryBuilder<BookSeries> bookSeriesQueryBuilder = bookSeriesQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		bookSeriesQueryBuilder.equal(BookSeries_.uid, uid);
		bookSeriesQueryBuilder.like(BookSeries_.title, criteria.getTitle());
		bookSeriesQueryBuilder.between(BookSeries_.publishedYearFrom, criteria.getPublishedYearFrom(), null);
		bookSeriesQueryBuilder.between(BookSeries_.publishedYearTo, null, criteria.getPublishedYearTo());
		bookSeriesQueryBuilder.between(BookSeries_.numberOfBooks, criteria.getNumberOfBooksFrom(), criteria.getNumberOfBooksTo());
		bookSeriesQueryBuilder.between(BookSeries_.yearFrom, criteria.getYearFrom(), null);
		bookSeriesQueryBuilder.between(BookSeries_.yearTo, null, criteria.getYearTo());
		bookSeriesQueryBuilder.equal(BookSeries_.miniseries, criteria.getMiniseries());
		bookSeriesQueryBuilder.equal(BookSeries_.eBookSeries, criteria.getEBookSeries());
		bookSeriesQueryBuilder.setSort(criteria.getSort());
		bookSeriesQueryBuilder.fetch(BookSeries_.parentSeries, doFetch);
		bookSeriesQueryBuilder.fetch(BookSeries_.childSeries, doFetch);
		bookSeriesQueryBuilder.fetch(BookSeries_.publishers, doFetch);
		bookSeriesQueryBuilder.divideQueries();
		bookSeriesQueryBuilder.fetch(BookSeries_.books, doFetch);

		return bookSeriesQueryBuilder.findPage();
	}

}
