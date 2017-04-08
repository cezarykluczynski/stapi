package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullResponse
import com.cezarykluczynski.stapi.server.series.reader.SeriesSoapReader
import spock.lang.Specification

class SeriesSoapEndpointTest extends Specification {

	private SeriesSoapReader seriesSoapReaderMock

	private SeriesSoapEndpoint seriesSoapEndpoint

	void setup() {
		seriesSoapReaderMock = Mock()
		seriesSoapEndpoint = new SeriesSoapEndpoint(seriesSoapReaderMock)
	}

	void "passes base call to SeriesSoapReader"() {
		given:
		SeriesBaseRequest seriesBaseRequest = Mock()
		SeriesBaseResponse seriesBaseResponse = Mock()

		when:
		SeriesBaseResponse seriesResponseResult = seriesSoapEndpoint.getSeriesBase(seriesBaseRequest)

		then:
		1 * seriesSoapReaderMock.readBase(seriesBaseRequest) >> seriesBaseResponse
		seriesResponseResult == seriesBaseResponse
	}

	void "passes full call to SeriesSoapReader"() {
		given:
		SeriesFullRequest seriesFullRequest = Mock()
		SeriesFullResponse seriesFullResponse = Mock()

		when:
		SeriesFullResponse seriesResponseResult = seriesSoapEndpoint.getSeriesFull(seriesFullRequest)

		then:
		1 * seriesSoapReaderMock.readFull(seriesFullRequest) >> seriesFullResponse
		seriesResponseResult == seriesFullResponse
	}

}
