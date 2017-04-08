package com.cezarykluczynski.stapi.server.comics.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullResponse
import com.cezarykluczynski.stapi.server.comics.reader.ComicsSoapReader
import spock.lang.Specification

class ComicsSoapEndpointTest extends Specification {

	private ComicsSoapReader comicsSoapReaderMock

	private ComicsSoapEndpoint comicsSoapEndpoint

	void setup() {
		comicsSoapReaderMock = Mock()
		comicsSoapEndpoint = new ComicsSoapEndpoint(comicsSoapReaderMock)
	}

	void "passes base call to ComicsSoapReader"() {
		given:
		ComicsBaseRequest comicsBaseRequest = Mock()
		ComicsBaseResponse comicsBaseResponse = Mock()

		when:
		ComicsBaseResponse comicsResponseResult = comicsSoapEndpoint.getComicsBase(comicsBaseRequest)

		then:
		1 * comicsSoapReaderMock.readBase(comicsBaseRequest) >> comicsBaseResponse
		comicsResponseResult == comicsBaseResponse
	}

	void "passes full call to ComicsSoapReader"() {
		given:
		ComicsFullRequest comicsFullRequest = Mock()
		ComicsFullResponse comicsFullResponse = Mock()

		when:
		ComicsFullResponse comicsResponseResult = comicsSoapEndpoint.getComicsFull(comicsFullRequest)

		then:
		1 * comicsSoapReaderMock.readFull(comicsFullRequest) >> comicsFullResponse
		comicsResponseResult == comicsFullResponse
	}

}
