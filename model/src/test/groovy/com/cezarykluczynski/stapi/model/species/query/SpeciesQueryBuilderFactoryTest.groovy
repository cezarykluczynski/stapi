package com.cezarykluczynski.stapi.model.species.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SpeciesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private SpeciesQueryBuilderFactory speciesQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "SpeciesQueryBuilder is created"() {
		when:
		speciesQueryBuilerFactory = new SpeciesQueryBuilderFactory(jpaContextMock)

		then:
		speciesQueryBuilerFactory != null
	}

}
