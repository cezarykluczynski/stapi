package com.cezarykluczynski.stapi.model.performer.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class PerformerQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private PerformerQueryBuilderFactory performerQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "PerformerQueryBuilderFactory is created"() {
		when:
		performerQueryBuilderFactory = new PerformerQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		performerQueryBuilderFactory != null
	}

}
