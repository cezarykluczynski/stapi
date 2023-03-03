package com.cezarykluczynski.stapi.server.magazine_series.endpoint

import com.cezarykluczynski.stapi.client.api.dto.MagazineSeriesSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MAGAZINE_SERIES)
})
class MagazineSeriesRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets magazine series by UID"() {
		when:
		MagazineSeriesFullResponse magazineSeriesFullResponse = stapiRestClient.magazineSeries.get('MSMA0000012747')

		then:
		magazineSeriesFullResponse.magazineSeries.title == 'Star Trek: Communicator'
	}

	void "'Sci-Fi & Fantasy Models' is among megazine series published between 1993 and 2001"() {
		given:
		MagazineSeriesSearchCriteria magazineSeriesSearchCriteria = new MagazineSeriesSearchCriteria(
				publishedYearFrom: 1993,
				publishedYearTo: 2001
		)

		when:
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = stapiRestClient.magazineSeries.search(magazineSeriesSearchCriteria)

		then:
		magazineSeriesBaseResponse.magazineSeries.stream().anyMatch { it.title == 'Sci-Fi & Fantasy Models' }
	}

}
