package com.cezarykluczynski.stapi.server.book_series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullResponse
import com.cezarykluczynski.stapi.server.book_series.reader.BookSeriesSoapReader
import spock.lang.Specification

class BookSeriesSoapEndpointTest extends Specification {

	private BookSeriesSoapReader bookSeriesSoapReaderMock

	private BookSeriesSoapEndpoint bookSeriesSoapEndpoint

	void setup() {
		bookSeriesSoapReaderMock = Mock()
		bookSeriesSoapEndpoint = new BookSeriesSoapEndpoint(bookSeriesSoapReaderMock)
	}

	void "passes base call to BookSeriesSoapReader"() {
		given:
		BookSeriesBaseRequest bookSeriesBaseRequest = Mock()
		BookSeriesBaseResponse bookSeriesBaseResponse = Mock()

		when:
		BookSeriesBaseResponse bookSeriesResponseResult = bookSeriesSoapEndpoint.getBookSeriesBase(bookSeriesBaseRequest)

		then:
		1 * bookSeriesSoapReaderMock.readBase(bookSeriesBaseRequest) >> bookSeriesBaseResponse
		bookSeriesResponseResult == bookSeriesBaseResponse
	}

	void "passes full call to BookSeriesSoapReader"() {
		given:
		BookSeriesFullRequest bookSeriesFullRequest = Mock()
		BookSeriesFullResponse bookSeriesFullResponse = Mock()

		when:
		BookSeriesFullResponse bookSeriesResponseResult = bookSeriesSoapEndpoint.getBookSeriesFull(bookSeriesFullRequest)

		then:
		1 * bookSeriesSoapReaderMock.readFull(bookSeriesFullRequest) >> bookSeriesFullResponse
		bookSeriesResponseResult == bookSeriesFullResponse
	}

}
