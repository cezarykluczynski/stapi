package com.cezarykluczynski.stapi.model.character.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CharacterQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CharacterQueryBuilderFactory characterQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "CharacterQueryBuilderFactory is created"() {
		when:
		characterQueryBuilderFactory = new CharacterQueryBuilderFactory(jpaContextMock)

		then:
		characterQueryBuilderFactory != null
	}

}
