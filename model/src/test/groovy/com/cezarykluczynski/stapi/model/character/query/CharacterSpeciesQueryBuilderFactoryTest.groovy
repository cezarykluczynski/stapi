package com.cezarykluczynski.stapi.model.character.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CharacterSpeciesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CharacterSpeciesQueryBuilderFactory characterSpeciesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "CharacterSpeciesQueryBuilderFactory is created"() {
		when:
		characterSpeciesQueryBuilderFactory = new CharacterSpeciesQueryBuilderFactory(jpaContextMock)

		then:
		characterSpeciesQueryBuilderFactory != null
	}

}
