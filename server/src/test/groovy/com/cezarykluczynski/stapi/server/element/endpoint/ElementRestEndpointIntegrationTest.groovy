package com.cezarykluczynski.stapi.server.element.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ELEMENTS)
})
class ElementRestEndpointIntegrationTest extends AbstractElementEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets element by UID"() {
		when:
		ElementFullResponse elementFullResponse = stapiRestClient.elementApi.elementGet('ELMA0000028971', null)

		then:
		elementFullResponse.element.name == 'Mazdaium'
	}

	void "Zeppo is in both World series and Mega series"() {
		when:
		ElementBaseResponse elementBaseResponse = stapiRestClient.elementApi.elementSearchPost(null, null, null, null, null, null, null, null, null,
				true, null, null, true)

		then:
		elementBaseResponse.elements
				.stream()
				.anyMatch { it.name == 'Zeppo' }
	}

}
