package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor

import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.cezarykluczynski.stapi.model.medical_condition.repository.MedicalConditionRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class MedicalConditionWriterTest extends Specification {

	private MedicalConditionRepository medicalConditionRepositoryMock

	private MedicalConditionWriter medicalConditionWriterMock

	void setup() {
		medicalConditionRepositoryMock = Mock()
		medicalConditionWriterMock = new MedicalConditionWriter(medicalConditionRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		MedicalCondition medicalCondition = new MedicalCondition()
		List<MedicalCondition> medicalConditionList = Lists.newArrayList(medicalCondition)

		when:
		medicalConditionWriterMock.write(new Chunk(medicalConditionList))

		then:
		1 * medicalConditionRepositoryMock.saveAll(medicalConditionList)
		0 * _
	}

}
