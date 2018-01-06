package com.cezarykluczynski.stapi.model.astronomical_object.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class AstronomicalObjectQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private AstronomicalObjectQueryBuilderFactory astronomicalObjectQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "AstronomicalObjectQueryBuilderFactory is created"() {
		when:
		astronomicalObjectQueryBuilderFactory = new AstronomicalObjectQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		astronomicalObjectQueryBuilderFactory != null
	}

}
