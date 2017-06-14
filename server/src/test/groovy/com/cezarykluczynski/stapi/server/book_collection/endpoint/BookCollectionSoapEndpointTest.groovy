package com.cezarykluczynski.stapi.server.book_collection.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullResponse
import com.cezarykluczynski.stapi.server.book_collection.reader.BookCollectionSoapReader
import spock.lang.Specification

class BookCollectionSoapEndpointTest extends Specification {

	private BookCollectionSoapReader bookCollectionSoapReaderMock

	private BookCollectionSoapEndpoint bookCollectionSoapEndpoint

	void setup() {
		bookCollectionSoapReaderMock = Mock()
		bookCollectionSoapEndpoint = new BookCollectionSoapEndpoint(bookCollectionSoapReaderMock)
	}

	void "passes base call to BookCollectionSoapReader"() {
		given:
		BookCollectionBaseRequest bookCollectionBaseRequest = Mock()
		BookCollectionBaseResponse bookCollectionBaseResponse = Mock()

		when:
		BookCollectionBaseResponse bookCollectionResponseResult = bookCollectionSoapEndpoint.getBookCollectionBase(bookCollectionBaseRequest)

		then:
		1 * bookCollectionSoapReaderMock.readBase(bookCollectionBaseRequest) >> bookCollectionBaseResponse
		bookCollectionResponseResult == bookCollectionBaseResponse
	}

	void "passes full call to BookCollectionSoapReader"() {
		given:
		BookCollectionFullRequest bookCollectionFullRequest = Mock()
		BookCollectionFullResponse bookCollectionFullResponse = Mock()

		when:
		BookCollectionFullResponse bookCollectionResponseResult = bookCollectionSoapEndpoint.getBookCollectionFull(bookCollectionFullRequest)

		then:
		1 * bookCollectionSoapReaderMock.readFull(bookCollectionFullRequest) >> bookCollectionFullResponse
		bookCollectionResponseResult == bookCollectionFullResponse
	}

}
