package com.cezarykluczynski.stapi.model.astronomicalObject.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class AstronomicalObjectQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private AstronomicalObjectQueryBuilderFactory astronomicalObjectQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "AstronomicalObjectQueryBuilder is created"() {
		when:
		astronomicalObjectQueryBuilerFactory = new AstronomicalObjectQueryBuilderFactory(jpaContextMock)

		then:
		astronomicalObjectQueryBuilerFactory != null
	}

}
