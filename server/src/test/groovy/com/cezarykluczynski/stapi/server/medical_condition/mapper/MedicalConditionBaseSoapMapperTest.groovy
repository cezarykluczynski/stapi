package com.cezarykluczynski.stapi.server.medical_condition.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBase
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MedicalConditionBaseSoapMapperTest extends AbstractMedicalConditionMapperTest {

	private MedicalConditionBaseSoapMapper medicalConditionBaseSoapMapper

	void setup() {
		medicalConditionBaseSoapMapper = Mappers.getMapper(MedicalConditionBaseSoapMapper)
	}

	void "maps SOAP MedicalConditionBaseRequest to MedicalConditionRequestDTO"() {
		given:
		MedicalConditionBaseRequest medicalConditionBaseRequest = new MedicalConditionBaseRequest(
				name: NAME,
				psychologicalCondition: PSYCHOLOGICAL_CONDITION)

		when:
		MedicalConditionRequestDTO medicalConditionRequestDTO = medicalConditionBaseSoapMapper.mapBase medicalConditionBaseRequest

		then:
		medicalConditionRequestDTO.name == NAME
		medicalConditionRequestDTO.psychologicalCondition == PSYCHOLOGICAL_CONDITION
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		MedicalCondition medicalCondition = createMedicalCondition()

		when:
		MedicalConditionBase medicalConditionBase = medicalConditionBaseSoapMapper.mapBase(Lists.newArrayList(medicalCondition))[0]

		then:
		medicalConditionBase.uid == UID
		medicalConditionBase.name == NAME
		medicalConditionBase.psychologicalCondition == PSYCHOLOGICAL_CONDITION
	}

}
