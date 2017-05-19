package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPECIES)
})
class SpeciesRestEndpointIntegrationTest extends AbstractSpeciesEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets species by UID"() {
		when:
		SpeciesFullResponse speciesFullResponse = stapiRestClient.speciesApi.speciesGet('SPMA0000039802', null)

		then:
		speciesFullResponse.species.name == 'Q'
	}

	void "finds Species 8472 by it's properties"() {
		when:
		SpeciesBaseResponse speciesBaseResponse = stapiRestClient.speciesApi.speciesSearchPost(null, null, null, null,  null, null, null, true, null,
				null, null, true, null, true, true, null, null)

		then:
		speciesBaseResponse.species.stream()
				.anyMatch { it -> it.name == 'Species 8472' }
	}

}
