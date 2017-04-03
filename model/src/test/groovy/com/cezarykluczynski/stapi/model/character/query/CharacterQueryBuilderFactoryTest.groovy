package com.cezarykluczynski.stapi.model.character.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CharacterQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private CharacterQueryBuilderFactory characterQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "CharacterQueryBuilder is created"() {
		when:
		characterQueryBuilderFactory = new CharacterQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		characterQueryBuilderFactory != null
	}

}
