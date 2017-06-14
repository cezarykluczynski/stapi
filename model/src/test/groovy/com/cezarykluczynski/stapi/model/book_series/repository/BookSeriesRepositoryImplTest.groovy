package com.cezarykluczynski.stapi.model.book_series.repository

import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries_
import com.cezarykluczynski.stapi.model.book_series.query.BookSeriesQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractBookSeriesTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class BookSeriesRepositoryImplTest extends AbstractBookSeriesTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private BookSeriesQueryBuilderFactory bookSeriesQueryBuilderFactory

	private BookSeriesRepositoryImpl bookSeriesRepositoryImpl

	private QueryBuilder<BookSeries> bookSeriesQueryBuilder

	private Pageable pageable

	private BookSeriesRequestDTO bookSeriesRequestDTO

	private BookSeries bookSeries

	private Page page

	void setup() {
		bookSeriesQueryBuilderFactory = Mock()
		bookSeriesRepositoryImpl = new BookSeriesRepositoryImpl(bookSeriesQueryBuilderFactory)
		bookSeriesQueryBuilder = Mock()
		pageable = Mock()
		bookSeriesRequestDTO = Mock()
		page = Mock()
		bookSeries = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = bookSeriesRepositoryImpl.findMatching(bookSeriesRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookSeriesQueryBuilderFactory.createQueryBuilder(pageable) >> bookSeriesQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * bookSeriesRequestDTO.uid >> UID
		1 * bookSeriesQueryBuilder.equal(BookSeries_.uid, UID)

		then: 'string criteria are set'
		1 * bookSeriesRequestDTO.title >> TITLE
		1 * bookSeriesQueryBuilder.like(BookSeries_.title, TITLE)

		then: 'integer criteria are set'
		1 * bookSeriesRequestDTO.publishedYearFrom >> PUBLISHED_YEAR_FROM
		1 * bookSeriesQueryBuilder.between(BookSeries_.publishedYearFrom, PUBLISHED_YEAR_FROM, null)
		1 * bookSeriesRequestDTO.publishedYearTo >> PUBLISHED_YEAR_TO
		1 * bookSeriesQueryBuilder.between(BookSeries_.publishedYearTo, null, PUBLISHED_YEAR_TO)

		1 * bookSeriesRequestDTO.numberOfBooksFrom >> NUMBER_OF_BOOKS_FROM
		1 * bookSeriesRequestDTO.numberOfBooksTo >> NUMBER_OF_BOOKS_TO
		1 * bookSeriesQueryBuilder.between(BookSeries_.numberOfBooks, NUMBER_OF_BOOKS_FROM, NUMBER_OF_BOOKS_TO)

		1 * bookSeriesRequestDTO.yearFrom >> YEAR_FROM
		1 * bookSeriesQueryBuilder.between(BookSeries_.yearFrom, YEAR_FROM, null)
		1 * bookSeriesRequestDTO.yearTo >> YEAR_TO
		1 * bookSeriesQueryBuilder.between(BookSeries_.yearTo, null, YEAR_TO)

		then: 'boolean criteria are set'
		1 * bookSeriesRequestDTO.miniseries >> MINISERIES
		1 * bookSeriesQueryBuilder.equal(BookSeries_.miniseries, MINISERIES)
		1 * bookSeriesRequestDTO.EBookSeries >> E_BOOK_SERIES
		1 * bookSeriesQueryBuilder.equal(BookSeries_.eBookSeries, E_BOOK_SERIES)

		then: 'sort is set'
		1 * bookSeriesRequestDTO.sort >> SORT
		1 * bookSeriesQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * bookSeriesQueryBuilder.fetch(BookSeries_.parentSeries, true)
		1 * bookSeriesQueryBuilder.fetch(BookSeries_.childSeries, true)
		1 * bookSeriesQueryBuilder.fetch(BookSeries_.publishers, true)
		1 * bookSeriesQueryBuilder.fetch(BookSeries_.books, true)

		then: 'page is retrieved'
		1 * bookSeriesQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = bookSeriesRepositoryImpl.findMatching(bookSeriesRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookSeriesQueryBuilderFactory.createQueryBuilder(pageable) >> bookSeriesQueryBuilder

		then: 'uid criteria is set to null'
		1 * bookSeriesRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * bookSeriesQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(bookSeries)
		1 * bookSeries.setParentSeries(Sets.newHashSet())
		1 * bookSeries.setChildSeries(Sets.newHashSet())
		1 * bookSeries.setPublishers(Sets.newHashSet())
		1 * bookSeries.setBooks(Sets.newHashSet())
		pageOutput == page
	}

}
