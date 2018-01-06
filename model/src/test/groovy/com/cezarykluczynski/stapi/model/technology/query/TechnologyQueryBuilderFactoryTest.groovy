package com.cezarykluczynski.stapi.model.technology.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TechnologyQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private TechnologyQueryBuilderFactory technologyQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "TechnologyQueryBuilderFactory is created"() {
		when:
		technologyQueryBuilderFactory = new TechnologyQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		technologyQueryBuilderFactory != null
	}

}
