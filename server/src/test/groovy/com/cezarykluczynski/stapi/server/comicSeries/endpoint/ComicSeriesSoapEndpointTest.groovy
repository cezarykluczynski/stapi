package com.cezarykluczynski.stapi.server.comicSeries.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesResponse
import com.cezarykluczynski.stapi.server.comicSeries.reader.ComicSeriesSoapReader
import spock.lang.Specification

class ComicSeriesSoapEndpointTest extends Specification {

	private ComicSeriesSoapReader comicSeriesSoapReaderMock

	private ComicSeriesSoapEndpoint comicSeriesSoapEndpoint

	void setup() {
		comicSeriesSoapReaderMock = Mock(ComicSeriesSoapReader)
		comicSeriesSoapEndpoint = new ComicSeriesSoapEndpoint(comicSeriesSoapReaderMock)
	}

	void "passes call to ComicSeriesSoapReader"() {
		given:
		ComicSeriesRequest comicSeriesRequest = Mock(ComicSeriesRequest)
		ComicSeriesResponse comicSeriesResponse = Mock(ComicSeriesResponse)

		when:
		ComicSeriesResponse comicSeriesResponseResult = comicSeriesSoapEndpoint.getComicSeries(comicSeriesRequest)

		then:
		1 * comicSeriesSoapReaderMock.read(comicSeriesRequest) >> comicSeriesResponse
		comicSeriesResponseResult == comicSeriesResponse
	}

}
