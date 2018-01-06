package com.cezarykluczynski.stapi.model.animal.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class AnimalQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private AnimalQueryBuilderFactory animalQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "AnimalQueryBuilderFactory is created"() {
		when:
		animalQueryBuilderFactory = new AnimalQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		animalQueryBuilderFactory != null
	}

}
