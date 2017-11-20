package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionPortType
import spock.lang.Specification

class BookCollectionTest extends Specification {

	private BookCollectionPortType bookCollectionPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private BookCollection bookCollection

	void setup() {
		bookCollectionPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		bookCollection = new BookCollection(bookCollectionPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		BookCollectionBaseRequest bookCollectionBaseRequest = Mock()
		BookCollectionBaseResponse bookCollectionBaseResponse = Mock()

		when:
		BookCollectionBaseResponse bookCollectionBaseResponseOutput = bookCollection.search(bookCollectionBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(bookCollectionBaseRequest)
		1 * bookCollectionPortTypeMock.getBookCollectionBase(bookCollectionBaseRequest) >> bookCollectionBaseResponse
		0 * _
		bookCollectionBaseResponse == bookCollectionBaseResponseOutput
	}

	void "searches entities"() {
		given:
		BookCollectionFullRequest bookCollectionFullRequest = Mock()
		BookCollectionFullResponse bookCollectionFullResponse = Mock()

		when:
		BookCollectionFullResponse bookCollectionFullResponseOutput = bookCollection.get(bookCollectionFullRequest)

		then:
		1 * apiKeySupplierMock.supply(bookCollectionFullRequest)
		1 * bookCollectionPortTypeMock.getBookCollectionFull(bookCollectionFullRequest) >> bookCollectionFullResponse
		0 * _
		bookCollectionFullResponse == bookCollectionFullResponseOutput
	}

}
