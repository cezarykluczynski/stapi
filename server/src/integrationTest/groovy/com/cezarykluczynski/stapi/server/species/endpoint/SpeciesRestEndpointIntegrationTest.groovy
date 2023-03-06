package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.api.dto.SpeciesV2SearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPECIES)
})
class SpeciesRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets species by UID"() {
		when:
		SpeciesV2FullResponse speciesV2FullResponse = stapiRestClient.species.getV2('SPMA0000039802')

		then:
		speciesV2FullResponse.species.name == 'Q'
	}

	void "gets humans by UID"() {
		when:
		SpeciesV2FullResponse speciesV2FullResponse = stapiRestClient.species.getV2('SPMA0000026314')

		then:
		speciesV2FullResponse.species.name == 'Human'
		speciesV2FullResponse.species.characters.size() > 1670
	}

	void "finds Species 8472 by it's properties"() {
		given:
		SpeciesV2SearchCriteria speciesV2SearchCriteria = new SpeciesV2SearchCriteria(
				extraGalacticSpecies: true,
				shapeshiftingSpecies: true,
				telepathicSpecies: true,
		)

		when:
		SpeciesV2BaseResponse speciesV2BaseResponse = stapiRestClient.species.searchV2(speciesV2SearchCriteria)

		then:
		speciesV2BaseResponse.species.stream()
				.anyMatch { it -> it.name == 'Species 8472' }
	}

}
