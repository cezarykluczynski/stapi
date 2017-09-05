package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPACECRAFT_CLASSES)
})
class SpacecraftClassRestEndpointIntegrationTest extends AbstractSpacecraftClassEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPECIES)
	})
	void "gets spacecraft class by UID"() {
		when:
		SpacecraftClassFullResponse spacecraftClassFullResponse = stapiRestClient.spacecraftClassApi.spacecraftClassGet('SCMA0000018879', null)

		then:
		spacecraftClassFullResponse.spacecraftClass.name == 'Cardassian freighter'
		spacecraftClassFullResponse.spacecraftClass.species.name == 'Cardassian'
	}

	void "'Tellarite freighter' is among Tellarite spacecraft classes"() {
		when:
		SpacecraftClassBaseResponse spacecraftClassBaseResponse = stapiRestClient.spacecraftClassApi.spacecraftClassSearchPost(null, null, null, null,
				'Tellarite', null, null)

		then:
		spacecraftClassBaseResponse.spacecraftClasses.stream()
				.anyMatch { it.name == 'Tellarite freighter' }
	}

}
