package com.cezarykluczynski.stapi.server.occupation.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_OCCUPATIONS)
})
class OccupationRestEndpointIntegrationTest extends AbstractOccupationEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets occupation by UID"() {
		when:
		OccupationFullResponse occupationFullResponse = stapiRestClient.occupationApi.occupationGet('OCMA0000221770', null)

		then:
		occupationFullResponse.occupation.name == 'Designer'
	}

	void "Coroner is among legal and medical occupations"() {
		when:
		OccupationBaseResponse occupationBaseResponse = stapiRestClient.occupationApi.occupationSearchPost(null, null, null, null, null, true, true,
				null)

		then:
		occupationBaseResponse.occupations
				.stream()
				.anyMatch { it.name == 'Coroner' }
	}

}
