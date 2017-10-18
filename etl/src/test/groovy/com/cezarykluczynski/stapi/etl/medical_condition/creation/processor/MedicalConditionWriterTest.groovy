package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor

import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.cezarykluczynski.stapi.model.medical_condition.repository.MedicalConditionRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class MedicalConditionWriterTest extends Specification {

	private MedicalConditionRepository medicalConditionRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private MedicalConditionWriter medicalConditionWriterMock

	void setup() {
		medicalConditionRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		medicalConditionWriterMock = new MedicalConditionWriter(medicalConditionRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		MedicalCondition medicalCondition = new MedicalCondition()
		List<MedicalCondition> medicalConditionList = Lists.newArrayList(medicalCondition)

		when:
		medicalConditionWriterMock.write(medicalConditionList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, MedicalCondition) >> { args ->
			assert args[0][0] == medicalCondition
			medicalConditionList
		}
		1 * medicalConditionRepositoryMock.save(medicalConditionList)
		0 * _
	}

}
