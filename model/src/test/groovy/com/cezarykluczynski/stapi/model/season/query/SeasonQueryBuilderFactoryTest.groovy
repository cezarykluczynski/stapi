package com.cezarykluczynski.stapi.model.season.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SeasonQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private SeasonQueryBuilderFactory seasonQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "SeasonQueryBuilderFactory is created"() {
		when:
		seasonQueryBuilderFactory = new SeasonQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		seasonQueryBuilderFactory != null
	}

}
