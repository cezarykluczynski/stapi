package com.cezarykluczynski.stapi.server.season.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBaseResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SEASONS)
})
class SeasonRestEndpointIntegrationTest extends AbstractSeasonEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets season by UID"() {
		when:
		SeasonFullResponse seasonFullResponse = stapiRestClient.seasonApi.seasonGet('SAMA0000001735', null)

		then:
		seasonFullResponse.season.title == 'DS9 Season 1'
	}

	void "find all seasons that are seventh in their series"() {
		when:
		SeasonBaseResponse seasonBaseResponse = stapiRestClient.seasonApi.seasonSearchPost(null, null, null, null, null, 7, 7, null, null)

		then:
		seasonBaseResponse.seasons.size() == 3
	}

}
