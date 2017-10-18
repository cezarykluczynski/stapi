package com.cezarykluczynski.stapi.server.medical_condition.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFull
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import org.mapstruct.factory.Mappers

class MedicalConditionFullSoapMapperTest extends AbstractMedicalConditionMapperTest {

	private MedicalConditionFullSoapMapper medicalConditionFullSoapMapper

	void setup() {
		medicalConditionFullSoapMapper = Mappers.getMapper(MedicalConditionFullSoapMapper)
	}

	void "maps SOAP MedicalConditionFullRequest to MedicalConditionBaseRequestDTO"() {
		given:
		MedicalConditionFullRequest medicalConditionFullRequest = new MedicalConditionFullRequest(uid: UID)

		when:
		MedicalConditionRequestDTO medicalConditionRequestDTO = medicalConditionFullSoapMapper.mapFull medicalConditionFullRequest

		then:
		medicalConditionRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		MedicalCondition medicalCondition = createMedicalCondition()

		when:
		MedicalConditionFull medicalConditionFull = medicalConditionFullSoapMapper.mapFull(medicalCondition)

		then:
		medicalConditionFull.uid == UID
		medicalConditionFull.name == NAME
		medicalConditionFull.psychologicalCondition == PSYCHOLOGICAL_CONDITION
	}

}
