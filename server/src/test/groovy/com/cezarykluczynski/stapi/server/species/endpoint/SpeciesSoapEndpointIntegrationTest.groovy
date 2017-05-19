package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPECIES)
})
class SpeciesSoapEndpointIntegrationTest extends AbstractSpeciesEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets species by UID"() {
		when:
		SpeciesFullResponse speciesFullResponse = stapiSoapClient.speciesPortType.getSpeciesFull(new SpeciesFullRequest(uid: 'SPMA0000006503'))

		then:
		speciesFullResponse.species.name == 'Ba\'ku'
	}

	void "gets species that are both humanoid and reptilian"() {
		when:
		SpeciesBaseResponse speciesBaseResponse = stapiSoapClient.speciesPortType.getSpeciesBase(new SpeciesBaseRequest(
				humanoidSpecies: true,
				reptilianSpecies: true
		))

		then:
		speciesBaseResponse.species.stream()
				.anyMatch { it -> it.name == 'Voth' }
	}

}
