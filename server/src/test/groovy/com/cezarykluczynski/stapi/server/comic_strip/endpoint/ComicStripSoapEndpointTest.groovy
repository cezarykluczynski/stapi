package com.cezarykluczynski.stapi.server.comic_strip.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse
import com.cezarykluczynski.stapi.server.comic_strip.reader.ComicStripSoapReader
import spock.lang.Specification

class ComicStripSoapEndpointTest extends Specification {

	private ComicStripSoapReader comicStripSoapReaderMock

	private ComicStripSoapEndpoint comicStripSoapEndpoint

	void setup() {
		comicStripSoapReaderMock = Mock()
		comicStripSoapEndpoint = new ComicStripSoapEndpoint(comicStripSoapReaderMock)
	}

	void "passes base call to ComicStripSoapReader"() {
		given:
		ComicStripBaseRequest comicStripBaseRequest = Mock()
		ComicStripBaseResponse comicStripBaseResponse = Mock()

		when:
		ComicStripBaseResponse comicStripResponseResult = comicStripSoapEndpoint.getComicStripBase(comicStripBaseRequest)

		then:
		1 * comicStripSoapReaderMock.readBase(comicStripBaseRequest) >> comicStripBaseResponse
		comicStripResponseResult == comicStripBaseResponse
	}

	void "passes full call to ComicStripSoapReader"() {
		given:
		ComicStripFullRequest comicStripFullRequest = Mock()
		ComicStripFullResponse comicStripFullResponse = Mock()

		when:
		ComicStripFullResponse comicStripResponseResult = comicStripSoapEndpoint.getComicStripFull(comicStripFullRequest)

		then:
		1 * comicStripSoapReaderMock.readFull(comicStripFullRequest) >> comicStripFullResponse
		comicStripResponseResult == comicStripFullResponse
	}

}
