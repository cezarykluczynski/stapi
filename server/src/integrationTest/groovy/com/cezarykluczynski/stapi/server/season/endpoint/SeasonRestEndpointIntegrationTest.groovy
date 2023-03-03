package com.cezarykluczynski.stapi.server.season.endpoint

import com.cezarykluczynski.stapi.client.api.dto.SeasonSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBaseResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SEASONS)
})
class SeasonRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets season by UID"() {
		when:
		SeasonFullResponse seasonFullResponse = stapiRestClient.season.get('SAMA0000001735')

		then:
		seasonFullResponse.season.title == 'DS9 Season 1'
	}

	void "find all seasons that are seventh in their series"() {
		given:
		SeasonSearchCriteria seasonSearchCriteria = new SeasonSearchCriteria(
				seasonNumberFrom: 7,
				seasonNumberTo: 7
		)

		when:
		SeasonBaseResponse seasonBaseResponse = stapiRestClient.season.search(seasonSearchCriteria)

		then:
		seasonBaseResponse.seasons.size() == 3
	}

}
