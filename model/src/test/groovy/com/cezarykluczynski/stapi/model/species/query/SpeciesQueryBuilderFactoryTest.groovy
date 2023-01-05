package com.cezarykluczynski.stapi.model.species.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SpeciesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private SpeciesQueryBuilderFactory speciesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "SpeciesQueryBuilderFactory is created"() {
		when:
		speciesQueryBuilderFactory = new SpeciesQueryBuilderFactory(jpaContextMock)

		then:
		speciesQueryBuilderFactory != null
	}

}
