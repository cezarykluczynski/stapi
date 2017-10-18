package com.cezarykluczynski.stapi.server.medical_condition.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionHeader
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MedicalConditionHeaderRestMapperTest extends AbstractMedicalConditionMapperTest {

	private MedicalConditionHeaderRestMapper medicalConditionHeaderRestMapper

	void setup() {
		medicalConditionHeaderRestMapper = Mappers.getMapper(MedicalConditionHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		MedicalCondition medicalCondition = new MedicalCondition(
				uid: UID,
				name: NAME)

		when:
		MedicalConditionHeader medicalConditionHeader = medicalConditionHeaderRestMapper.map(Lists.newArrayList(medicalCondition))[0]

		then:
		medicalConditionHeader.uid == UID
		medicalConditionHeader.name == NAME
	}

}
