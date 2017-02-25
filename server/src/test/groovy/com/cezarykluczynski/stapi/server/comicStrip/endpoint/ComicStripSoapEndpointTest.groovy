package com.cezarykluczynski.stapi.server.comicStrip.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripResponse
import com.cezarykluczynski.stapi.server.comicStrip.reader.ComicStripSoapReader
import spock.lang.Specification

class ComicStripSoapEndpointTest extends Specification {

	private ComicStripSoapReader comicStripSoapReaderMock

	private ComicStripSoapEndpoint comicStripSoapEndpoint

	void setup() {
		comicStripSoapReaderMock = Mock(ComicStripSoapReader)
		comicStripSoapEndpoint = new ComicStripSoapEndpoint(comicStripSoapReaderMock)
	}

	void "passes call to ComicStripSoapReader"() {
		given:
		ComicStripRequest comicStripRequest = Mock(ComicStripRequest)
		ComicStripResponse comicStripResponse = Mock(ComicStripResponse)

		when:
		ComicStripResponse comicStripResponseResult = comicStripSoapEndpoint.getComicStrips(comicStripRequest)

		then:
		1 * comicStripSoapReaderMock.read(comicStripRequest) >> comicStripResponse
		comicStripResponseResult == comicStripResponse
	}

}
