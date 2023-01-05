package com.cezarykluczynski.stapi.model.astronomical_object.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class AstronomicalObjectQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private AstronomicalObjectQueryBuilderFactory astronomicalObjectQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "AstronomicalObjectQueryBuilderFactory is created"() {
		when:
		astronomicalObjectQueryBuilderFactory = new AstronomicalObjectQueryBuilderFactory(jpaContextMock)

		then:
		astronomicalObjectQueryBuilderFactory != null
	}

}
