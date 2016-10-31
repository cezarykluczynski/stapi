package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.rest.model.SeriesResponse
import com.cezarykluczynski.stapi.server.series.common.EndpointIntegrationTest

class SeriesRestEndpointIntegrationTest extends EndpointIntegrationTest {

	def setup() {
		createRestClient()
	}

	def "gets all series"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		SeriesResponse seriesResponse = stapiRestClient.seriesApi.seriesGet(pageNumber, pageSize)

		then:
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
		seriesResponse.series.size() == 6
	}

	def "gets series by title"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 2

		when:
		SeriesResponse seriesResponse = stapiRestClient.seriesApi.seriesPost(pageNumber, pageSize, "Voyager")

		then:
		seriesResponse.series.size() == 1
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
	}

}
