package com.cezarykluczynski.stapi.server.comic_series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.server.comic_series.reader.ComicSeriesSoapReader
import spock.lang.Specification

class ComicSeriesSoapEndpointTest extends Specification {

	private ComicSeriesSoapReader comicSeriesSoapReaderMock

	private ComicSeriesSoapEndpoint comicSeriesSoapEndpoint

	void setup() {
		comicSeriesSoapReaderMock = Mock()
		comicSeriesSoapEndpoint = new ComicSeriesSoapEndpoint(comicSeriesSoapReaderMock)
	}

	void "passes base call to ComicSeriesSoapReader"() {
		given:
		ComicSeriesBaseRequest comicSeriesBaseRequest = Mock()
		ComicSeriesBaseResponse comicSeriesBaseResponse = Mock()

		when:
		ComicSeriesBaseResponse comicSeriesResponseResult = comicSeriesSoapEndpoint.getComicSeriesBase(comicSeriesBaseRequest)

		then:
		1 * comicSeriesSoapReaderMock.readBase(comicSeriesBaseRequest) >> comicSeriesBaseResponse
		comicSeriesResponseResult == comicSeriesBaseResponse
	}

	void "passes full call to ComicSeriesSoapReader"() {
		given:
		ComicSeriesFullRequest comicSeriesFullRequest = Mock()
		ComicSeriesFullResponse comicSeriesFullResponse = Mock()

		when:
		ComicSeriesFullResponse comicSeriesResponseResult = comicSeriesSoapEndpoint.getComicSeriesFull(comicSeriesFullRequest)

		then:
		1 * comicSeriesSoapReaderMock.readFull(comicSeriesFullRequest) >> comicSeriesFullResponse
		comicSeriesResponseResult == comicSeriesFullResponse
	}

}
