package com.cezarykluczynski.stapi.server.spacecraft.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPACECRAFTS)
})
class SpacecraftRestEndpointIntegrationTest extends AbstractSpacecraftEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets spacecraft by UID"() {
		when:
		SpacecraftFullResponse spacecraftFullResponse = stapiRestClient.spacecraftApi.spacecraftGet('SRMA0000128784', null)

		then:
		spacecraftFullResponse.spacecraft.name == 'Kobayashi Maru'
	}

	void "'NCC-1701-A' is among USS Enterprises"() {
		when:
		SpacecraftBaseResponse spacecraftBaseResponse = stapiRestClient.spacecraftApi.spacecraftSearchPost(null, null, null, null,
				'USS Enterprise')

		then:
		spacecraftBaseResponse.spacecrafts.stream()
				.anyMatch { it.registry == 'NCC-1701-A' }
	}

}
