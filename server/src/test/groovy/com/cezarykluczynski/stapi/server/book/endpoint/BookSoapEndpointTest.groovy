package com.cezarykluczynski.stapi.server.book.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookFullResponse
import com.cezarykluczynski.stapi.server.book.reader.BookSoapReader
import spock.lang.Specification

class BookSoapEndpointTest extends Specification {

	private BookSoapReader bookSoapReaderMock

	private BookSoapEndpoint bookSoapEndpoint

	void setup() {
		bookSoapReaderMock = Mock()
		bookSoapEndpoint = new BookSoapEndpoint(bookSoapReaderMock)
	}

	void "passes base call to BookSoapReader"() {
		given:
		BookBaseRequest bookBaseRequest = Mock()
		BookBaseResponse bookBaseResponse = Mock()

		when:
		BookBaseResponse bookResponseResult = bookSoapEndpoint.getBookBase(bookBaseRequest)

		then:
		1 * bookSoapReaderMock.readBase(bookBaseRequest) >> bookBaseResponse
		bookResponseResult == bookBaseResponse
	}

	void "passes full call to BookSoapReader"() {
		given:
		BookFullRequest bookFullRequest = Mock()
		BookFullResponse bookFullResponse = Mock()

		when:
		BookFullResponse bookResponseResult = bookSoapEndpoint.getBookFull(bookFullRequest)

		then:
		1 * bookSoapReaderMock.readFull(bookFullRequest) >> bookFullResponse
		bookResponseResult == bookFullResponse
	}

}
