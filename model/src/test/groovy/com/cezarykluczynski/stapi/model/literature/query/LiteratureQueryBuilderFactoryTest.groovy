package com.cezarykluczynski.stapi.model.literature.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class LiteratureQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private LiteratureQueryBuilderFactory literatureQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "LiteratureQueryBuilderFactory is created"() {
		when:
		literatureQueryBuilderFactory = new LiteratureQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		literatureQueryBuilderFactory != null
	}

}
