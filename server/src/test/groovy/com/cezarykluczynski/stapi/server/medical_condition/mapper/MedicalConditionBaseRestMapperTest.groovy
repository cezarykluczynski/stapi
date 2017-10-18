package com.cezarykluczynski.stapi.server.medical_condition.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBase
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.cezarykluczynski.stapi.server.medical_condition.dto.MedicalConditionRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MedicalConditionBaseRestMapperTest extends AbstractMedicalConditionMapperTest {

	private MedicalConditionBaseRestMapper medicalConditionBaseRestMapper

	void setup() {
		medicalConditionBaseRestMapper = Mappers.getMapper(MedicalConditionBaseRestMapper)
	}

	void "maps MedicalConditionRestBeanParams to MedicalConditionRequestDTO"() {
		given:
		MedicalConditionRestBeanParams medicalConditionRestBeanParams = new MedicalConditionRestBeanParams(
				uid: UID,
				name: NAME,
				psychologicalCondition: PSYCHOLOGICAL_CONDITION)

		when:
		MedicalConditionRequestDTO medicalConditionRequestDTO = medicalConditionBaseRestMapper.mapBase medicalConditionRestBeanParams

		then:
		medicalConditionRequestDTO.uid == UID
		medicalConditionRequestDTO.name == NAME
		medicalConditionRequestDTO.psychologicalCondition == PSYCHOLOGICAL_CONDITION
	}

	void "maps DB entity to base REST entity"() {
		given:
		MedicalCondition medicalCondition = createMedicalCondition()

		when:
		MedicalConditionBase medicalConditionBase = medicalConditionBaseRestMapper.mapBase(Lists.newArrayList(medicalCondition))[0]

		then:
		medicalConditionBase.uid == UID
		medicalConditionBase.name == NAME
		medicalConditionBase.psychologicalCondition == PSYCHOLOGICAL_CONDITION
	}

}
