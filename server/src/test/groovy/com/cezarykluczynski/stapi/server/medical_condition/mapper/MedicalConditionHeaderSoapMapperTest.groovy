package com.cezarykluczynski.stapi.server.medical_condition.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionHeader
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MedicalConditionHeaderSoapMapperTest extends AbstractMedicalConditionMapperTest {

	private MedicalConditionHeaderSoapMapper medicalConditionHeaderSoapMapper

	void setup() {
		medicalConditionHeaderSoapMapper = Mappers.getMapper(MedicalConditionHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		MedicalCondition medicalCondition = new MedicalCondition(
				uid: UID,
				name: NAME)

		when:
		MedicalConditionHeader medicalConditionHeader = medicalConditionHeaderSoapMapper.map(Lists.newArrayList(medicalCondition))[0]

		then:
		medicalConditionHeader.uid == UID
		medicalConditionHeader.name == NAME
	}

}
