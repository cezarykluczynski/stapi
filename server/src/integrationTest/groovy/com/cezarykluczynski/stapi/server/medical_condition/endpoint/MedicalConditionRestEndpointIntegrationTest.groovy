package com.cezarykluczynski.stapi.server.medical_condition.endpoint

import com.cezarykluczynski.stapi.client.api.dto.MedicalConditionSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MEDICAL_CONDITIONS)
})
class MedicalConditionRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets medicalCondition by UID"() {
		when:
		MedicalConditionFullResponse medicalConditionFullResponse = stapiRestClient.medicalCondition.get('MEMA0000120294')

		then:
		medicalConditionFullResponse.medicalCondition.name == 'Photosensitivity'
	}

	void "Nervous breakdown can be found by name"() {
		given:
		MedicalConditionSearchCriteria medicalConditionSearchCriteria = new MedicalConditionSearchCriteria(
				name: 'Nervous breakdown'
		)

		when:
		MedicalConditionBaseResponse medicalConditionBaseResponse = stapiRestClient.medicalCondition.search(medicalConditionSearchCriteria)

		then:
		medicalConditionBaseResponse.medicalConditions
				.stream()
				.anyMatch { it.name == 'Nervous breakdown' }
	}

}
