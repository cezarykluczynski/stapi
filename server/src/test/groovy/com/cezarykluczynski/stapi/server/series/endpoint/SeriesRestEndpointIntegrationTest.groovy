package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesResponse
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
		SeriesResponse seriesResponse = stapiRestClient.seriesApi.seriesPost(pageNumber, pageSize, null, "Voyager", null,
				null, null, null, null, null, null, null, null)

		then:
		seriesResponse.series.size() == 1
		seriesResponse.series[0].title == "Star Trek: Voyager"
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
	}

	def "gets series by id"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 2

		when:
		SeriesResponse seriesResponse = stapiRestClient.seriesApi.seriesPost(pageNumber, pageSize, 1, null, null,
				null, null, null, null, null, null, null, null)

		then:
		seriesResponse.series.size() == 1
		seriesResponse.series[0].abbreviation == "TAS"
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
	}

}
