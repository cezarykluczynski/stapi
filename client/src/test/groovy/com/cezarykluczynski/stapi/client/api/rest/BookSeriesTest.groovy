package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.BookSeriesApi
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFullResponse
import com.cezarykluczynski.stapi.util.AbstractBookSeriesTest

class BookSeriesTest extends AbstractBookSeriesTest {

	private BookSeriesApi bookSeriesApiMock

	private BookSeries bookSeries

	void setup() {
		bookSeriesApiMock = Mock()
		bookSeries = new BookSeries(bookSeriesApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		BookSeriesFullResponse bookSeriesFullResponse = Mock()

		when:
		BookSeriesFullResponse bookSeriesFullResponseOutput = bookSeries.get(UID)

		then:
		1 * bookSeriesApiMock.bookSeriesGet(UID, API_KEY) >> bookSeriesFullResponse
		0 * _
		bookSeriesFullResponse == bookSeriesFullResponseOutput
	}

	void "searches entities"() {
		given:
		BookSeriesBaseResponse bookSeriesBaseResponse = Mock()

		when:
		BookSeriesBaseResponse bookSeriesBaseResponseOutput = bookSeries.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_BOOKS_FROM, NUMBER_OF_BOOKS_TO, YEAR_FROM, YEAR_TO, MINISERIES, E_BOOK_SERIES)

		then:
		1 * bookSeriesApiMock.bookSeriesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_BOOKS_FROM, NUMBER_OF_BOOKS_TO, YEAR_FROM, YEAR_TO, MINISERIES, E_BOOK_SERIES) >> bookSeriesBaseResponse
		0 * _
		bookSeriesBaseResponse == bookSeriesBaseResponseOutput
	}

}
