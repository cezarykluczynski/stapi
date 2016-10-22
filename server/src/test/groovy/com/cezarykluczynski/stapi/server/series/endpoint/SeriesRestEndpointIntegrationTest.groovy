package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestClient
import com.cezarykluczynski.stapi.server.series.common.EndpointIntegrationTest
import org.springframework.boot.context.embedded.LocalServerPort

class SeriesRestEndpointIntegrationTest extends EndpointIntegrationTest {

	@LocalServerPort
	private Integer localServerPost

	private StapiRestClient stapiRestClient

	def setup() {
		stapiRestClient = new StapiRestClient("http://localhost:${localServerPost}/stapi/")
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
