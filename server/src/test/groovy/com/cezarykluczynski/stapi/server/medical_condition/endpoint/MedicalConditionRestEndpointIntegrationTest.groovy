package com.cezarykluczynski.stapi.server.medical_condition.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MEDICAL_CONDITIONS)
})
class MedicalConditionRestEndpointIntegrationTest extends AbstractMedicalConditionEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets medicalCondition by UID"() {
		when:
		MedicalConditionFullResponse medicalConditionFullResponse = stapiRestClient.medicalConditionApi.medicalConditionGet('MEMA0000120294', null)

		then:
		medicalConditionFullResponse.medicalCondition.name == 'Photosensitivity'
	}

	void "Nervous breakdown can be found by name"() {
		when:
		MedicalConditionBaseResponse medicalConditionBaseResponse = stapiRestClient.medicalConditionApi.medicalConditionSearchPost(null, null, null,
				null, 'Nervous breakdown', null)

		then:
		medicalConditionBaseResponse.medicalConditions
				.stream()
				.anyMatch { it.name == 'Nervous breakdown' }
	}

}
