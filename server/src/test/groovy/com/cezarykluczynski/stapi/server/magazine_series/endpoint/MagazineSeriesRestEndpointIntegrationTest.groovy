package com.cezarykluczynski.stapi.server.magazine_series.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MAGAZINE_SERIES)
})
class MagazineSeriesRestEndpointIntegrationTest extends AbstractMagazineSeriesEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets magazine series by UID"() {
		when:
		MagazineSeriesFullResponse magazineSeriesFullResponse = stapiRestClient.magazineSeriesApi.magazineSeriesGet('MSMA0000012747', null)

		then:
		magazineSeriesFullResponse.magazineSeries.title == 'Star Trek: Communicator'
	}

	void "'Sci-Fi & Fantasy Models' is among megazine series published between 1993 and 2001"() {
		when:
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = stapiRestClient.magazineSeriesApi
				.magazineSeriesSearchPost(0, 20, null, null, null, 1993, 2001, null, null)

		then:
		magazineSeriesBaseResponse.magazineSeries.stream().anyMatch { it.title == 'Sci-Fi & Fantasy Models' }
	}

}
