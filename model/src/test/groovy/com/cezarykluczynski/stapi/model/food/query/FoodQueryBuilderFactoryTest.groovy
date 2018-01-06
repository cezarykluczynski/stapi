package com.cezarykluczynski.stapi.model.food.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class FoodQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private FoodQueryBuilderFactory foodQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "FoodQueryBuilderFactory is created"() {
		when:
		foodQueryBuilderFactory = new FoodQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		foodQueryBuilderFactory != null
	}

}
