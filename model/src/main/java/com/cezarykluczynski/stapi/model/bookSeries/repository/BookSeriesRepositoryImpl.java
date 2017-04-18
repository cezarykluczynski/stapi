package com.cezarykluczynski.stapi.model.bookSeries.repository;

import com.cezarykluczynski.stapi.model.bookSeries.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries_;
import com.cezarykluczynski.stapi.model.bookSeries.query.BookSeriesQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class BookSeriesRepositoryImpl extends AbstractRepositoryImpl<BookSeries> implements BookSeriesRepositoryCustom {

	private BookSeriesQueryBuilderFactory bookSeriesQueryBuilderFactory;

	@Inject
	public BookSeriesRepositoryImpl(BookSeriesQueryBuilderFactory bookSeriesQueryBuilderFactory) {
		this.bookSeriesQueryBuilderFactory = bookSeriesQueryBuilderFactory;
	}

	@Override
	public Page<BookSeries> findMatching(BookSeriesRequestDTO criteria, Pageable pageable) {
		QueryBuilder<BookSeries> bookSeriesQueryBuilder = bookSeriesQueryBuilderFactory.createQueryBuilder(pageable);
		String guid = criteria.getGuid();
		boolean doFetch = guid != null;

		bookSeriesQueryBuilder.equal(BookSeries_.guid, guid);
		bookSeriesQueryBuilder.like(BookSeries_.title, criteria.getTitle());
		bookSeriesQueryBuilder.between(BookSeries_.publishedYearFrom, criteria.getPublishedYearFrom(), null);
		bookSeriesQueryBuilder.between(BookSeries_.publishedYearTo, null, criteria.getPublishedYearTo());
		bookSeriesQueryBuilder.between(BookSeries_.numberOfBooks, criteria.getNumberOfBooksFrom(), criteria.getNumberOfBooksTo());
		bookSeriesQueryBuilder.between(BookSeries_.yearFrom, criteria.getYearFrom(), null);
		bookSeriesQueryBuilder.between(BookSeries_.yearTo, null, criteria.getYearTo());
		bookSeriesQueryBuilder.between(BookSeries_.stardateFrom, criteria.getStardateFrom(), null);
		bookSeriesQueryBuilder.between(BookSeries_.stardateTo, null, criteria.getStardateTo());
		bookSeriesQueryBuilder.equal(BookSeries_.miniseries, criteria.getMiniseries());
		bookSeriesQueryBuilder.setSort(criteria.getSort());
		bookSeriesQueryBuilder.fetch(BookSeries_.parentSeries, doFetch);
		bookSeriesQueryBuilder.fetch(BookSeries_.childSeries, doFetch);
		bookSeriesQueryBuilder.fetch(BookSeries_.publishers, doFetch);
		bookSeriesQueryBuilder.fetch(BookSeries_.books, doFetch);

		Page<BookSeries> bookSeriesPage = bookSeriesQueryBuilder.findPage();
		clearProxies(bookSeriesPage, !doFetch);
		return bookSeriesPage;
	}

	@Override
	protected void clearProxies(Page<BookSeries> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(episode -> {
			episode.setParentSeries(Sets.newHashSet());
			episode.setChildSeries(Sets.newHashSet());
			episode.setPublishers(Sets.newHashSet());
			episode.setBooks(Sets.newHashSet());
		});
	}

}
