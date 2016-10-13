package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.soap.SeriesRequest
import com.cezarykluczynski.stapi.client.soap.SeriesResponse
import com.cezarykluczynski.stapi.server.series.reader.SeriesSoapReader
import spock.lang.Specification

class SeriesSoapEndpointTest extends Specification {

	private SeriesSoapReader seriesSoapReaderMock

	private SeriesSoapEndpoint seriesSoapEndpoint

	def setup() {
		seriesSoapReaderMock = Mock(SeriesSoapReader)
		seriesSoapEndpoint = new SeriesSoapEndpoint(seriesSoapReaderMock)
	}

	def "passes call to SeriesSoapReader"() {
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
