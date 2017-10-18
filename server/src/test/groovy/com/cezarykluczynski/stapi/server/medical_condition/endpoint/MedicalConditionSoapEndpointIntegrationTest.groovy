package com.cezarykluczynski.stapi.server.medical_condition.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MEDICAL_CONDITIONS)
})
class MedicalConditionSoapEndpointIntegrationTest extends AbstractMedicalConditionEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets medicalCondition by UID"() {
		when:
		MedicalConditionFullResponse medicalConditionFullResponse = stapiSoapClient.medicalConditionPortType
				.getMedicalConditionFull(new MedicalConditionFullRequest(uid: 'MEMA0000082562'))

		then:
		medicalConditionFullResponse.medicalCondition.name == 'Chromovirus'
	}

	void "Claustrophobia is among psychological conditions"() {
		when:
		MedicalConditionBaseResponse medicalConditionFullResponse = stapiSoapClient.medicalConditionPortType
				.getMedicalConditionBase(new MedicalConditionBaseRequest(
						name: 'Claustrophobia',
						psychologicalCondition: true
				))

		then:
		medicalConditionFullResponse.medicalConditions
				.stream()
				.anyMatch { it.name == 'Claustrophobia' }
	}

}
