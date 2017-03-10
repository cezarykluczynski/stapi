package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.Species
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesResponse
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

	@SuppressWarnings('ClosureAsLastMethodParameter')
	void "gets species that are both humanoid and reptilian"() {
		when:
		SpeciesResponse speciesResponse = stapiSoapClient.speciesPortType.getSpecies(new SpeciesRequest(
				humanoidSpecies: true,
				reptilianSpecies: true
		))
		Optional<Species> vothSpecies = speciesResponse.species.stream()
				.filter({ it -> it.name == 'Voth' })
				.findFirst()

		then:
		vothSpecies.isPresent()
		vothSpecies.get().homeworld.name == 'Earth'
	}

}
