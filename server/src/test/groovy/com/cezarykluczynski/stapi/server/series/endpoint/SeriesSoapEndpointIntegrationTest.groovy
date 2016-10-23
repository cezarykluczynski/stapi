package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.soap.SeriesRequest
import com.cezarykluczynski.stapi.client.soap.SeriesResponse
import com.cezarykluczynski.stapi.server.series.common.EndpointIntegrationTest

class SeriesSoapEndpointIntegrationTest extends EndpointIntegrationTest {

	def setup() {
		createSoapClient()
	}

	def "gets all series"() {
		when:
		SeriesResponse seriesResponse = stapiSoapClient.seriesPortType.getSeries(new SeriesRequest())

		then:
		seriesResponse.series.size() == 6
	}

	def "gets series by title"() {
		expect:
		stapiSoapClient.seriesPortType.getSeries(new SeriesRequest(title: "Voyager")).series.size() == 1
	}

}
