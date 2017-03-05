package com.cezarykluczynski.stapi.model.character.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CharacterSpeciesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CharacterSpeciesQueryBuilderFactory characterSpeciesQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "CharacterSpeciesQueryBuilder is created"() {
		when:
		characterSpeciesQueryBuilerFactory = new CharacterSpeciesQueryBuilderFactory(jpaContextMock)

		then:
		characterSpeciesQueryBuilerFactory != null
	}

}
