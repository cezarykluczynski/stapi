package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.server.series.common.EndpointIntegrationTest

class SeriesRestEndpointIntegrationTest extends EndpointIntegrationTest {

	def setup() {
		createRestClient()
	}

	def "gets all series"() {
		expect:
		stapiRestClient.seriesApi.seriesGet().size() == 6
	}

	def "gets series by title"() {
		expect:
		stapiRestClient.seriesApi.seriesPost("Voyager").size() == 1
	}

}
