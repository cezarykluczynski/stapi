package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripPortType
import spock.lang.Specification

class ComicStripTest extends Specification {

	private ComicStripPortType comicStripPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private ComicStrip comicStrip

	void setup() {
		comicStripPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		comicStrip = new ComicStrip(comicStripPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		ComicStripBaseRequest comicStripBaseRequest = Mock()
		ComicStripBaseResponse comicStripBaseResponse = Mock()

		when:
		ComicStripBaseResponse comicStripBaseResponseOutput = comicStrip.search(comicStripBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(comicStripBaseRequest)
		1 * comicStripPortTypeMock.getComicStripBase(comicStripBaseRequest) >> comicStripBaseResponse
		0 * _
		comicStripBaseResponse == comicStripBaseResponseOutput
	}

	void "searches entities"() {
		given:
		ComicStripFullRequest comicStripFullRequest = Mock()
		ComicStripFullResponse comicStripFullResponse = Mock()

		when:
		ComicStripFullResponse comicStripFullResponseOutput = comicStrip.get(comicStripFullRequest)

		then:
		1 * apiKeySupplierMock.supply(comicStripFullRequest)
		1 * comicStripPortTypeMock.getComicStripFull(comicStripFullRequest) >> comicStripFullResponse
		0 * _
		comicStripFullResponse == comicStripFullResponseOutput
	}

}
