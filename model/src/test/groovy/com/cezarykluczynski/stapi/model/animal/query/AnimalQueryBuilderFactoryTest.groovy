package com.cezarykluczynski.stapi.model.animal.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class AnimalQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private AnimalQueryBuilderFactory animalQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "AnimalQueryBuilderFactory is created"() {
		when:
		animalQueryBuilderFactory = new AnimalQueryBuilderFactory(jpaContextMock)

		then:
		animalQueryBuilderFactory != null
	}

}
