package com.cezarykluczynski.stapi.server.medical_condition.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFull
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import org.mapstruct.factory.Mappers

class MedicalConditionFullRestMapperTest extends AbstractMedicalConditionMapperTest {

	private MedicalConditionFullRestMapper medicalConditionFullRestMapper

	void setup() {
		medicalConditionFullRestMapper = Mappers.getMapper(MedicalConditionFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		MedicalCondition medicalCondition = createMedicalCondition()

		when:
		MedicalConditionFull medicalConditionFull = medicalConditionFullRestMapper.mapFull(medicalCondition)

		then:
		medicalConditionFull.uid == UID
		medicalConditionFull.name == NAME
		medicalConditionFull.psychologicalCondition == PSYCHOLOGICAL_CONDITION
	}

}
