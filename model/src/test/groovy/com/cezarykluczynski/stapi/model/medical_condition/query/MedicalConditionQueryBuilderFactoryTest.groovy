package com.cezarykluczynski.stapi.model.medical_condition.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MedicalConditionQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private MedicalConditionQueryBuilderFactory medicalConditionQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "MedicalConditionQueryBuilderFactory is created"() {
		when:
		medicalConditionQueryBuilderFactory = new MedicalConditionQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		medicalConditionQueryBuilderFactory != null
	}

}
