package com.cezarykluczynski.stapi.server.comics.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicsRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsResponse
import com.cezarykluczynski.stapi.server.comics.reader.ComicsSoapReader
import spock.lang.Specification

class ComicsSoapEndpointTest extends Specification {

	private ComicsSoapReader comicsSoapReaderMock

	private ComicsSoapEndpoint comicsSoapEndpoint

	void setup() {
		comicsSoapReaderMock = Mock(ComicsSoapReader)
		comicsSoapEndpoint = new ComicsSoapEndpoint(comicsSoapReaderMock)
	}

	void "passes call to ComicsSoapReader"() {
		given:
		ComicsRequest comicsRequest = Mock(ComicsRequest)
		ComicsResponse comicsResponse = Mock(ComicsResponse)

		when:
		ComicsResponse comicsResponseResult = comicsSoapEndpoint.getComics(comicsRequest)

		then:
		1 * comicsSoapReaderMock.readBase(comicsRequest) >> comicsResponse
		comicsResponseResult == comicsResponse
	}

}
