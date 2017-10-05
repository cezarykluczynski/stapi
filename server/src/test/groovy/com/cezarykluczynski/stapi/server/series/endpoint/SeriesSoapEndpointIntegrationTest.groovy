package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SERIES)
})
class SeriesSoapEndpointIntegrationTest extends AbstractSeriesEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES)
	})
	void "gets series by uid"() {
		when:
		SeriesFullResponse seriesFullResponse = stapiSoapClient.seriesPortType.getSeriesFull(new SeriesFullRequest(
				uid: UID
		))

		then:
		seriesFullResponse.series.uid == UID
		seriesFullResponse.series.abbreviation == TAS
	}

	void "gets all series"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		SeriesBaseResponse seriesResponse = stapiSoapClient.seriesPortType.getSeriesBase(new SeriesBaseRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize
				)
		))

		then:
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
		seriesResponse.series.size() == 7

	}

	void "gets series by title"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 2

		when:
		SeriesBaseResponse seriesResponse = stapiSoapClient.seriesPortType.getSeriesBase(new SeriesBaseRequest(
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

}
