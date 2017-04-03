package com.cezarykluczynski.stapi.model.performer.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class PerformerQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private PerformerQueryBuilderFactory performerQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "PerformerQueryBuilder is created"() {
		when:
		performerQueryBuilderFactory = new PerformerQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		performerQueryBuilderFactory != null
	}

}
