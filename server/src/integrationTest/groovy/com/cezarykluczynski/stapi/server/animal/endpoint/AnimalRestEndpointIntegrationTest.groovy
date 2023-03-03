package com.cezarykluczynski.stapi.server.animal.endpoint

import com.cezarykluczynski.stapi.client.api.dto.AnimalSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ANIMALS)
})
class AnimalRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets animal by UID"() {
		when:
		AnimalFullResponse animalFullResponse = stapiRestClient.animal.get('ANMA0000027723')

		then:
		animalFullResponse.animal.name == 'Turtle'
	}

	void "Parrot is an earth avian"() {
		given:
		AnimalSearchCriteria animalSearchCriteria = new AnimalSearchCriteria(
				earthAnimal: true,
				avian: true
		)

		when:
		AnimalBaseResponse animalBaseResponse = stapiRestClient.animal.search(animalSearchCriteria)

		then:
		animalBaseResponse.animals
				.stream()
				.anyMatch { it.name == 'Parrot' }
	}

}
