package com.cezarykluczynski.stapi.model.comics.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicsQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private ComicsQueryBuilderFactory comicsQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "ComicsQueryBuilderFactory is created"() {
		when:
		comicsQueryBuilderFactory = new ComicsQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		comicsQueryBuilderFactory != null
	}

}
