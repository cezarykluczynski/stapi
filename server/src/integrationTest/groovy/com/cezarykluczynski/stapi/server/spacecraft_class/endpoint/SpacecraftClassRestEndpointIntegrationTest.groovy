package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint

import com.cezarykluczynski.stapi.client.api.dto.SpacecraftClassV2SearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV3FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPACECRAFT_CLASSES)
})
class SpacecraftClassRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPECIES)
	})
	void "gets spacecraft class by UID"() {
		when:
		SpacecraftClassV3FullResponse spacecraftClassV2FullResponse = stapiRestClient.spacecraftClass.getV3('SCMA0000022032')

		then:
		spacecraftClassV2FullResponse.spacecraftClass.name == 'Cardassian supply ship'
		spacecraftClassV2FullResponse.spacecraftClass.species.name == 'Cardassian'
	}

	void "'Tellarite freighter' is among Tellarite spacecraft classes"() {
		given:
		SpacecraftClassV2SearchCriteria spacecraftClassV2SearchCriteria = new SpacecraftClassV2SearchCriteria(
				name: 'Tellarite'
		)

		when:
		SpacecraftClassV2BaseResponse spacecraftClassBaseResponse = stapiRestClient.spacecraftClass.searchV2(spacecraftClassV2SearchCriteria)

		then:
		spacecraftClassBaseResponse.spacecraftClasses.stream()
				.anyMatch { it.name == 'Tellarite freighter' }
	}

}
