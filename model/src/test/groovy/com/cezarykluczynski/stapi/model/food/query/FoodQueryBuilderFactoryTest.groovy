package com.cezarykluczynski.stapi.model.food.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class FoodQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private FoodQueryBuilderFactory foodQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "FoodQueryBuilderFactory is created"() {
		when:
		foodQueryBuilderFactory = new FoodQueryBuilderFactory(jpaContextMock)

		then:
		foodQueryBuilderFactory != null
	}

}
