package com.cezarykluczynski.stapi.server.animal.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ANIMALS)
})
class AnimalSoapEndpointIntegrationTest extends AbstractAnimalEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets animal by UID"() {
		when:
		AnimalFullResponse animalFullResponse = stapiSoapClient.animalPortType.getAnimalFull(new AnimalFullRequest(uid: 'ANMA0000044740'))

		then:
		animalFullResponse.animal.name == 'Arctic spider'
	}

	void "Betazoid cat is not an feline of earthly origin"() {
		when:
		AnimalBaseResponse animalFullResponse = stapiSoapClient.animalPortType.getAnimalBase(new AnimalBaseRequest(
				earthAnimal: false,
				feline: true
		))

		then:
		animalFullResponse.animals
				.stream()
				.anyMatch { it.name == 'Betazoid cat' }
	}

}
