package com.cezarykluczynski.stapi.model.food.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class FoodQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private FoodQueryBuilderFactory foodQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "FoodQueryBuilder is created"() {
		when:
		foodQueryBuilderFactory = new FoodQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		foodQueryBuilderFactory != null
	}

}
