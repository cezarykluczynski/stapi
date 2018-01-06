package com.cezarykluczynski.stapi.model.book.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class BookQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private BookQueryBuilderFactory bookQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "BookQueryBuilderFactory is created"() {
		when:
		bookQueryBuilderFactory = new BookQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		bookQueryBuilderFactory != null
	}

}
