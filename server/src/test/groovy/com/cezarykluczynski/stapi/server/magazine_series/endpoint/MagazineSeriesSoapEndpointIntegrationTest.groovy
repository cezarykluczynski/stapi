package com.cezarykluczynski.stapi.server.magazine_series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MAGAZINE_SERIES)
})
class MagazineSeriesSoapEndpointIntegrationTest extends AbstractMagazineSeriesEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets magazine by UID"() {
		when:
		MagazineSeriesFullResponse magazineSeriesFullResponse = stapiSoapClient.magazineSeriesPortType
				.getMagazineSeriesFull(new MagazineSeriesFullRequest(
				uid: 'MSMA0000006226'
		))

		then:
		magazineSeriesFullResponse.magazineSeries.title == 'Star Trek: The Magazine'
	}

	void "'The Official Star Trek: The Next Generation Magazine' is found when queries for magazine series with 25-35 issues"() {
		when:
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = stapiSoapClient.magazineSeriesPortType
				.getMagazineSeriesBase(new MagazineSeriesBaseRequest(
						numberOfIssues: new IntegerRange(
								from: 25,
								to: 35)))

		then:
		magazineSeriesBaseResponse.magazineSeries.stream().anyMatch { it.title == 'The Official Star Trek: The Next Generation Magazine' }
	}

}
