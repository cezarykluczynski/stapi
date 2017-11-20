package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesPortType
import spock.lang.Specification

class BookSeriesTest extends Specification {

	private BookSeriesPortType bookSeriesPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private BookSeries bookSeries

	void setup() {
		bookSeriesPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		bookSeries = new BookSeries(bookSeriesPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		BookSeriesBaseRequest bookSeriesBaseRequest = Mock()
		BookSeriesBaseResponse bookSeriesBaseResponse = Mock()

		when:
		BookSeriesBaseResponse bookSeriesBaseResponseOutput = bookSeries.search(bookSeriesBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(bookSeriesBaseRequest)
		1 * bookSeriesPortTypeMock.getBookSeriesBase(bookSeriesBaseRequest) >> bookSeriesBaseResponse
		0 * _
		bookSeriesBaseResponse == bookSeriesBaseResponseOutput
	}

	void "searches entities"() {
		given:
		BookSeriesFullRequest bookSeriesFullRequest = Mock()
		BookSeriesFullResponse bookSeriesFullResponse = Mock()

		when:
		BookSeriesFullResponse bookSeriesFullResponseOutput = bookSeries.get(bookSeriesFullRequest)

		then:
		1 * apiKeySupplierMock.supply(bookSeriesFullRequest)
		1 * bookSeriesPortTypeMock.getBookSeriesFull(bookSeriesFullRequest) >> bookSeriesFullResponse
		0 * _
		bookSeriesFullResponse == bookSeriesFullResponseOutput
	}

}
