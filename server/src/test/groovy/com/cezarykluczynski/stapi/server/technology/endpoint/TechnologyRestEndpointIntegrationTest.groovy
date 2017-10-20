package com.cezarykluczynski.stapi.server.technology.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TECHNOLOGY)
})
class TechnologyRestEndpointIntegrationTest extends AbstractTechnologyEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets technology by UID"() {
		when:
		TechnologyFullResponse technologyFullResponse = stapiRestClient.technologyApi.technologyGet('TEMA0000041574', null)

		then:
		technologyFullResponse.technology.name == 'Klingon Imperial information net'
	}

	void "Emergency Medical Hologram Replacement Program is a medical equipment related to computer programming"() {
		when:
		TechnologyBaseResponse technologyBaseResponse = stapiRestClient.technologyApi.technologySearchPost(null, null, null, null, null, null, null,
				null, null, true, null, null, null, null, null, null, null, null, null, null, null, null, null, true, null)

		then:
		technologyBaseResponse.technology
				.stream()
				.anyMatch { it.name == 'Emergency Medical Hologram Replacement Program' }
	}

}
