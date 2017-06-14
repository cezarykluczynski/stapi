package com.cezarykluczynski.stapi.server.comic_collection.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.server.comic_collection.reader.ComicCollectionSoapReader
import spock.lang.Specification

class ComicCollectionSoapEndpointTest extends Specification {

	private ComicCollectionSoapReader comicCollectionSoapReaderMock

	private ComicCollectionSoapEndpoint comicCollectionSoapEndpoint

	void setup() {
		comicCollectionSoapReaderMock = Mock()
		comicCollectionSoapEndpoint = new ComicCollectionSoapEndpoint(comicCollectionSoapReaderMock)
	}

	void "passes base call to ComicCollectionSoapReader"() {
		given:
		ComicCollectionBaseRequest comicCollectionBaseRequest = Mock()
		ComicCollectionBaseResponse comicCollectionBaseResponse = Mock()

		when:
		ComicCollectionBaseResponse comicCollectionResponseResult = comicCollectionSoapEndpoint.getComicCollectionBase(comicCollectionBaseRequest)

		then:
		1 * comicCollectionSoapReaderMock.readBase(comicCollectionBaseRequest) >> comicCollectionBaseResponse
		comicCollectionResponseResult == comicCollectionBaseResponse
	}

	void "passes full call to ComicCollectionSoapReader"() {
		given:
		ComicCollectionFullRequest comicCollectionFullRequest = Mock()
		ComicCollectionFullResponse comicCollectionFullResponse = Mock()

		when:
		ComicCollectionFullResponse comicCollectionResponseResult = comicCollectionSoapEndpoint.getComicCollectionFull(comicCollectionFullRequest)

		then:
		1 * comicCollectionSoapReaderMock.readFull(comicCollectionFullRequest) >> comicCollectionFullResponse
		comicCollectionResponseResult == comicCollectionFullResponse
	}

}
