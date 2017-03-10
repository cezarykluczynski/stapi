package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesResponse
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

	void "gets species by GUID"() {
		when:
		SpeciesResponse speciesResponse = stapiRestClient.speciesApi.speciesPost(null, null, null, 'SPMA0000039802', null, null, null, null, null,
				null, null, null, null, null, null, null, null)

		then:
		speciesResponse.species[0].name == 'Q'
	}

}
