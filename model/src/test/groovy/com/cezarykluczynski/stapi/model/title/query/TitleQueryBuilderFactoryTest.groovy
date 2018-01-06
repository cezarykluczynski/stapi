package com.cezarykluczynski.stapi.model.title.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TitleQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private TitleQueryBuilderFactory titleQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "TitleQueryBuilderFactory is created"() {
		when:
		titleQueryBuilderFactory = new TitleQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		titleQueryBuilderFactory != null
	}

}
