package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType
import spock.lang.Specification

class ComicSeriesTest extends Specification {

	private ComicSeriesPortType comicSeriesPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private ComicSeries comicSeries

	void setup() {
		comicSeriesPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		comicSeries = new ComicSeries(comicSeriesPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		ComicSeriesBaseRequest comicSeriesBaseRequest = Mock()
		ComicSeriesBaseResponse comicSeriesBaseResponse = Mock()

		when:
		ComicSeriesBaseResponse comicSeriesBaseResponseOutput = comicSeries.search(comicSeriesBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(comicSeriesBaseRequest)
		1 * comicSeriesPortTypeMock.getComicSeriesBase(comicSeriesBaseRequest) >> comicSeriesBaseResponse
		0 * _
		comicSeriesBaseResponse == comicSeriesBaseResponseOutput
	}

	void "searches entities"() {
		given:
		ComicSeriesFullRequest comicSeriesFullRequest = Mock()
		ComicSeriesFullResponse comicSeriesFullResponse = Mock()

		when:
		ComicSeriesFullResponse comicSeriesFullResponseOutput = comicSeries.get(comicSeriesFullRequest)

		then:
		1 * apiKeySupplierMock.supply(comicSeriesFullRequest)
		1 * comicSeriesPortTypeMock.getComicSeriesFull(comicSeriesFullRequest) >> comicSeriesFullResponse
		0 * _
		comicSeriesFullResponse == comicSeriesFullResponseOutput
	}

}
