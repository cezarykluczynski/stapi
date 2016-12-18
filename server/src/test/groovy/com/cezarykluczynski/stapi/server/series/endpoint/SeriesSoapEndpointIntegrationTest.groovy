package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.SeriesRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SERIES)
})
class SeriesSoapEndpointIntegrationTest extends AbstractSeriesEndpointIntegrationTest {

	def setup() {
		createSoapClient()
	}

	def "gets all series"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		SeriesResponse seriesResponse = stapiSoapClient.seriesPortType.getSeries(new SeriesRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize
				)
		))

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
		SeriesResponse seriesResponse = stapiSoapClient.seriesPortType.getSeries(new SeriesRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize
				),
				title: VOYAGER
		))

		then:
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
		seriesResponse.series.size() == 1
		seriesResponse.series[0].title.contains VOYAGER
	}

	def "gets series by guid"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 2

		when:
		SeriesResponse seriesResponse = stapiSoapClient.seriesPortType.getSeries(new SeriesRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize
				),
				guid: GUID
		))

		then:
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
		seriesResponse.series.size() == 1
		seriesResponse.series[0].guid == GUID
		seriesResponse.series[0].episodeHeaders.size() == 22
		seriesResponse.series[0].abbreviation == TAS
	}



}
