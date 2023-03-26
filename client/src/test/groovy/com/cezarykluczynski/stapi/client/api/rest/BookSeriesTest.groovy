package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.BookSeriesApi
import com.cezarykluczynski.stapi.client.rest.model.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.BookSeriesFullResponse
import com.cezarykluczynski.stapi.client.rest.model.BookSeriesSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractBookSeriesTest

class BookSeriesTest extends AbstractBookSeriesTest {

	private BookSeriesApi bookSeriesApiMock

	private BookSeries bookSeries

	void setup() {
		bookSeriesApiMock = Mock()
		bookSeries = new BookSeries(bookSeriesApiMock)
	}

	void "gets single entity"() {
		given:
		BookSeriesFullResponse bookSeriesFullResponse = Mock()

		when:
		BookSeriesFullResponse bookSeriesFullResponseOutput = bookSeries.get(UID)

		then:
		1 * bookSeriesApiMock.v1GetBookSeries(UID) >> bookSeriesFullResponse
		0 * _
		bookSeriesFullResponse == bookSeriesFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		BookSeriesBaseResponse bookSeriesBaseResponse = Mock()
		BookSeriesSearchCriteria bookSeriesSearchCriteria = new BookSeriesSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfBooksFrom: NUMBER_OF_BOOKS_FROM,
				numberOfBooksTo: NUMBER_OF_BOOKS_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				miniseries: MINISERIES,
				ebookSeries: E_BOOK_SERIES)
		bookSeriesSearchCriteria.sort = SORT

		when:
		BookSeriesBaseResponse bookSeriesBaseResponseOutput = bookSeries.search(bookSeriesSearchCriteria)

		then:
		1 * bookSeriesApiMock.v1SearchBookSeries(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_BOOKS_FROM, NUMBER_OF_BOOKS_TO, YEAR_FROM, YEAR_TO, MINISERIES, E_BOOK_SERIES) >> bookSeriesBaseResponse
		0 * _
		bookSeriesBaseResponse == bookSeriesBaseResponseOutput
	}

}
