package com.cezarykluczynski.stapi.model.book_collection.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class BookCollectionQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private BookCollectionQueryBuilderFactory bookCollectionQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "BookCollectionQueryBuilderFactory is created"() {
		when:
		bookCollectionQueryBuilderFactory = new BookCollectionQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		bookCollectionQueryBuilderFactory != null
	}

}
