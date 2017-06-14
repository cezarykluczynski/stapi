package com.cezarykluczynski.stapi.server.book_series.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFullResponse
import com.cezarykluczynski.stapi.server.book_series.dto.BookSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.book_series.reader.BookSeriesRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class BookSeriesRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private BookSeriesRestReader bookSeriesRestReaderMock

	private BookSeriesRestEndpoint bookSeriesRestEndpoint

	void setup() {
		bookSeriesRestReaderMock = Mock()
		bookSeriesRestEndpoint = new BookSeriesRestEndpoint(bookSeriesRestReaderMock)
	}

	void "passes get call to BookSeriesRestReader"() {
		given:
		BookSeriesFullResponse bookSeriesFullResponse = Mock()

		when:
		BookSeriesFullResponse bookSeriesFullResponseOutput = bookSeriesRestEndpoint.getBookSeries(UID)

		then:
		1 * bookSeriesRestReaderMock.readFull(UID) >> bookSeriesFullResponse
		bookSeriesFullResponseOutput == bookSeriesFullResponse
	}

	void "passes search get call to BookSeriesRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		BookSeriesBaseResponse bookSeriesResponse = Mock()

		when:
		BookSeriesBaseResponse bookSeriesResponseOutput = bookSeriesRestEndpoint.searchBookSeries(pageAwareBeanParams)

		then:
		1 * bookSeriesRestReaderMock.readBase(_ as BookSeriesRestBeanParams) >> { BookSeriesRestBeanParams bookSeriesRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			bookSeriesResponse
		}
		bookSeriesResponseOutput == bookSeriesResponse
	}

	void "passes search post call to BookSeriesRestReader"() {
		given:
		BookSeriesRestBeanParams bookSeriesRestBeanParams = new BookSeriesRestBeanParams(title: TITLE)
		BookSeriesBaseResponse bookSeriesResponse = Mock()

		when:
		BookSeriesBaseResponse bookSeriesResponseOutput = bookSeriesRestEndpoint.searchBookSeries(bookSeriesRestBeanParams)

		then:
		1 * bookSeriesRestReaderMock.readBase(bookSeriesRestBeanParams as BookSeriesRestBeanParams) >> { BookSeriesRestBeanParams params ->
			assert params.title == TITLE
			bookSeriesResponse
		}
		bookSeriesResponseOutput == bookSeriesResponse
	}

}
