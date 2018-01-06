package com.cezarykluczynski.stapi.model.occupation.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class OccupationQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private OccupationQueryBuilderFactory occupationQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "OccupationQueryBuilderFactory is created"() {
		when:
		occupationQueryBuilderFactory = new OccupationQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		occupationQueryBuilderFactory != null
	}

}
