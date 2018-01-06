package com.cezarykluczynski.stapi.model.conflict.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ConflictQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private ConflictQueryBuilderFactory conflictQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "ConflictQueryBuilderFactory is created"() {
		when:
		conflictQueryBuilderFactory = new ConflictQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		conflictQueryBuilderFactory != null
	}

}
