package com.cezarykluczynski.stapi.server.spacecraft.endpoint

import com.cezarykluczynski.stapi.client.api.dto.SpacecraftV2SearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPACECRAFTS)
})
class SpacecraftRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets spacecraft by UID"() {
		when:
		SpacecraftV2FullResponse spacecraftV2FullResponse = stapiRestClient.spacecraft.getV2('SRMA0000128784')

		then:
		spacecraftV2FullResponse.spacecraft.name == 'Kobayashi Maru'
	}

	void "'NCC-1701-A' is among USS Enterprises"() {
		given:
		SpacecraftV2SearchCriteria spacecraftV2SearchCriteria = new SpacecraftV2SearchCriteria(
				name: 'USS Enterprise'
		)

		when:
		SpacecraftV2BaseResponse spacecraftV2BaseResponse = stapiRestClient.spacecraft.searchV2(spacecraftV2SearchCriteria)

		then:
		spacecraftV2BaseResponse.spacecrafts.stream()
				.anyMatch { it.registry == 'NCC-1701-A' }
	}

}
