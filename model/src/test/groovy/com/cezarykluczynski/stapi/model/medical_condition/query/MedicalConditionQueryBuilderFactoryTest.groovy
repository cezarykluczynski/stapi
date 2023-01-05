package com.cezarykluczynski.stapi.model.medical_condition.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MedicalConditionQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private MedicalConditionQueryBuilderFactory medicalConditionQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "MedicalConditionQueryBuilderFactory is created"() {
		when:
		medicalConditionQueryBuilderFactory = new MedicalConditionQueryBuilderFactory(jpaContextMock)

		then:
		medicalConditionQueryBuilderFactory != null
	}

}
