package com.cezarykluczynski.stapi.model.food.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class FoodQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private FoodQueryBuilderFactory foodQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "FoodQueryBuilder is created"() {
		when:
		foodQueryBuilerFactory = new FoodQueryBuilderFactory(jpaContextMock)

		then:
		foodQueryBuilerFactory != null
	}

}
