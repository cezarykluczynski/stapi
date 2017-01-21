package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SeriesRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesResponse
import com.cezarykluczynski.stapi.server.series.reader.SeriesSoapReader
import spock.lang.Specification

class SeriesSoapEndpointTest extends Specification {

	private SeriesSoapReader seriesSoapReaderMock

	private SeriesSoapEndpoint seriesSoapEndpoint

	void setup() {
		seriesSoapReaderMock = Mock(SeriesSoapReader)
		seriesSoapEndpoint = new SeriesSoapEndpoint(seriesSoapReaderMock)
	}

	void "passes call to SeriesSoapReader"() {
		given:
		SeriesRequest seriesRequest = Mock(SeriesRequest)
		SeriesResponse seriesResponse = Mock(SeriesResponse)

		when:
		SeriesResponse seriesResponseResult = seriesSoapEndpoint.getSeries(seriesRequest)

		then:
		1 * seriesSoapReaderMock.read(seriesRequest) >> seriesResponse
		seriesResponseResult == seriesResponse
	}

}
