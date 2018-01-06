package com.cezarykluczynski.stapi.model.character.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CharacterQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private CharacterQueryBuilderFactory characterQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "CharacterQueryBuilderFactory is created"() {
		when:
		characterQueryBuilderFactory = new CharacterQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		characterQueryBuilderFactory != null
	}

}
