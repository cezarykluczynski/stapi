package com.cezarykluczynski.stapi.model.character.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CharacterQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CharacterQueryBuilderFactory characterQueryBuilerFactory

	def setup() {
		jpaContextMock = Mock(JpaContext)
	}

	def "CharacterQueryBuilder is created"() {
		when:
		characterQueryBuilerFactory = new CharacterQueryBuilderFactory(jpaContextMock)

		then:
		characterQueryBuilerFactory != null
	}

}
