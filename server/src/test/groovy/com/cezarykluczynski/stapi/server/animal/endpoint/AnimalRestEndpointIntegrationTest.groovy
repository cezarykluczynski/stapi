package com.cezarykluczynski.stapi.server.animal.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ANIMALS)
})
class AnimalRestEndpointIntegrationTest extends AbstractAnimalEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets animal by UID"() {
		when:
		AnimalFullResponse animalFullResponse = stapiRestClient.animalApi.animalGet('ANMA0000027723', null)

		then:
		animalFullResponse.animal.name == 'Turtle'
	}

	void "Parrot is an earth avian"() {
		when:
		AnimalBaseResponse animalBaseResponse = stapiRestClient.animalApi.animalSearchPost(null, null, null, null, null, true, null, true, null, null)

		then:
		animalBaseResponse.animals
				.stream()
				.anyMatch { it.name == 'Parrot' }
	}

}
