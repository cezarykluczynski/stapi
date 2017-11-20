package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookPortType
import spock.lang.Specification

class BookTest extends Specification {

	private BookPortType bookPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Book book

	void setup() {
		bookPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		book = new Book(bookPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		BookBaseRequest bookBaseRequest = Mock()
		BookBaseResponse bookBaseResponse = Mock()

		when:
		BookBaseResponse bookBaseResponseOutput = book.search(bookBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(bookBaseRequest)
		1 * bookPortTypeMock.getBookBase(bookBaseRequest) >> bookBaseResponse
		0 * _
		bookBaseResponse == bookBaseResponseOutput
	}

	void "searches entities"() {
		given:
		BookFullRequest bookFullRequest = Mock()
		BookFullResponse bookFullResponse = Mock()

		when:
		BookFullResponse bookFullResponseOutput = book.get(bookFullRequest)

		then:
		1 * apiKeySupplierMock.supply(bookFullRequest)
		1 * bookPortTypeMock.getBookFull(bookFullRequest) >> bookFullResponse
		0 * _
		bookFullResponse == bookFullResponseOutput
	}

}
