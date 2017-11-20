package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionPortType
import spock.lang.Specification

class ComicCollectionTest extends Specification {

	private ComicCollectionPortType comicCollectionPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private ComicCollection comicCollection

	void setup() {
		comicCollectionPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		comicCollection = new ComicCollection(comicCollectionPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		ComicCollectionBaseRequest comicCollectionBaseRequest = Mock()
		ComicCollectionBaseResponse comicCollectionBaseResponse = Mock()

		when:
		ComicCollectionBaseResponse comicCollectionBaseResponseOutput = comicCollection.search(comicCollectionBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(comicCollectionBaseRequest)
		1 * comicCollectionPortTypeMock.getComicCollectionBase(comicCollectionBaseRequest) >> comicCollectionBaseResponse
		0 * _
		comicCollectionBaseResponse == comicCollectionBaseResponseOutput
	}

	void "searches entities"() {
		given:
		ComicCollectionFullRequest comicCollectionFullRequest = Mock()
		ComicCollectionFullResponse comicCollectionFullResponse = Mock()

		when:
		ComicCollectionFullResponse comicCollectionFullResponseOutput = comicCollection.get(comicCollectionFullRequest)

		then:
		1 * apiKeySupplierMock.supply(comicCollectionFullRequest)
		1 * comicCollectionPortTypeMock.getComicCollectionFull(comicCollectionFullRequest) >> comicCollectionFullResponse
		0 * _
		comicCollectionFullResponse == comicCollectionFullResponseOutput
	}

}
