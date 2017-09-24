package com.cezarykluczynski.stapi.server.material.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MATERIALS)
})
class MaterialSoapEndpointIntegrationTest extends AbstractMaterialEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets material by UID"() {
		when:
		MaterialFullResponse materialFullResponse = stapiSoapClient.materialPortType.getMaterialFull(new MaterialFullRequest(uid: 'MTMA0000044147'))

		then:
		materialFullResponse.material.name == 'Eisillium'
	}

	void "Trevium is among mineral fuels"() {
		when:
		MaterialBaseResponse materialFullResponse = stapiSoapClient.materialPortType.getMaterialBase(new MaterialBaseRequest(
				mineral: true,
				fuel: true
		))

		then:
		materialFullResponse.materials
				.stream()
				.anyMatch { it.name == 'Trevium' }
	}

}
