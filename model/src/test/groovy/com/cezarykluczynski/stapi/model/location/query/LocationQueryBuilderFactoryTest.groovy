package com.cezarykluczynski.stapi.model.location.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class LocationQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private LocationQueryBuilderFactory locationQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "LocationQueryBuilderFactory is created"() {
		when:
		locationQueryBuilderFactory = new LocationQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		locationQueryBuilderFactory != null
	}

}
