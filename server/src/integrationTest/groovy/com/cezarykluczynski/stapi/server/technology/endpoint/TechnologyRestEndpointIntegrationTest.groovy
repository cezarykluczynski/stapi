package com.cezarykluczynski.stapi.server.technology.endpoint

import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2SearchCriteria
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TECHNOLOGY)
})
class TechnologyRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets technology by UID"() {
		when:
		TechnologyV2FullResponse technologyV2FullResponse = stapiRestClient.technology.getV2('TEMA0000041574')

		then:
		technologyV2FullResponse.technology.name == 'Klingon Imperial information net'
	}

	void "Emergency Medical Hologram Replacement Program is a medical equipment related to computer programming"() {
		given:
		TechnologyV2SearchCriteria technologyV2SearchCriteria = new TechnologyV2SearchCriteria(
				medicalEquipment: true,
				computerProgramming: true
		)

		when:
		TechnologyV2BaseResponse technologyV2BaseResponse = stapiRestClient.technology.searchV2(technologyV2SearchCriteria)

		then:
		technologyV2BaseResponse.technology
				.stream()
				.anyMatch { it.name == 'Emergency Medical Hologram Replacement Program' }
	}

}
