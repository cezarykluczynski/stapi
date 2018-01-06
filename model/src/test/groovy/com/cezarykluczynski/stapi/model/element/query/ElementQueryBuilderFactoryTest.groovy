package com.cezarykluczynski.stapi.model.element.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ElementQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private ElementQueryBuilderFactory elementQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "ElementQueryBuilderFactory is created"() {
		when:
		elementQueryBuilderFactory = new ElementQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		elementQueryBuilderFactory != null
	}

}
