package com.cezarykluczynski.stapi.server.material.endpoint

import com.cezarykluczynski.stapi.client.api.dto.MaterialSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.MaterialFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MATERIALS)
})
class MaterialRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets material by UID"() {
		when:
		MaterialFullResponse materialFullResponse = stapiRestClient.material.get('MTMA0000007096')

		then:
		materialFullResponse.material.name == 'Uridium'
	}

	void "bilitrium is among explosive minerals"() {
		given:
		MaterialSearchCriteria materialSearchCriteria = new MaterialSearchCriteria(
				explosive: true,
				mineral: true
		)

		when:
		MaterialBaseResponse materialBaseResponse = stapiRestClient.material.search(materialSearchCriteria)

		then:
		materialBaseResponse.materials
				.stream()
				.anyMatch { it.name == 'Bilitrium' }
	}

}
