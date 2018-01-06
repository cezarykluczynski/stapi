package com.cezarykluczynski.stapi.model.character.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CharacterSpeciesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private CharacterSpeciesQueryBuilderFactory characterSpeciesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "CharacterSpeciesQueryBuilderFactory is created"() {
		when:
		characterSpeciesQueryBuilderFactory = new CharacterSpeciesQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		characterSpeciesQueryBuilderFactory != null
	}

}
