package com.cezarykluczynski.stapi.server.comicCollection.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionResponse
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionSoapReader
import spock.lang.Specification

class ComicCollectionSoapEndpointTest extends Specification {

	private ComicCollectionSoapReader comicCollectionSoapReaderMock

	private ComicCollectionSoapEndpoint comicCollectionSoapEndpoint

	void setup() {
		comicCollectionSoapReaderMock = Mock(ComicCollectionSoapReader)
		comicCollectionSoapEndpoint = new ComicCollectionSoapEndpoint(comicCollectionSoapReaderMock)
	}

	void "passes call to ComicCollectionSoapReader"() {
		given:
		ComicCollectionRequest comicCollectionRequest = Mock(ComicCollectionRequest)
		ComicCollectionResponse comicCollectionResponse = Mock(ComicCollectionResponse)

		when:
		ComicCollectionResponse comicCollectionResponseResult = comicCollectionSoapEndpoint.getComicCollections(comicCollectionRequest)

		then:
		1 * comicCollectionSoapReaderMock.read(comicCollectionRequest) >> comicCollectionResponse
		comicCollectionResponseResult == comicCollectionResponse
	}

}
