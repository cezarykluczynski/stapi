package com.cezarykluczynski.stapi.server.material.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MATERIALS)
})
class MaterialRestEndpointIntegrationTest extends AbstractMaterialEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets material by UID"() {
		when:
		MaterialFullResponse materialFullResponse = stapiRestClient.materialApi.materialGet('MTMA0000007096', null)

		then:
		materialFullResponse.material.name == 'Uridium'
	}

	void "confirm that bilitrium is among explosive minerals"() {
		when:
		MaterialBaseResponse materialBaseResponse = stapiRestClient.materialApi.materialSearchPost(null, null, null, null, null, null, null, null,
				null, true, null, null, null, true, null)

		then:
		materialBaseResponse.materials
				.stream()
				.anyMatch { it.name == 'Bilitrium' }
	}

}
