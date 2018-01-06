package com.cezarykluczynski.stapi.model.comic_collection.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicCollectionQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private ComicCollectionQueryBuilderFactory comicCollectionQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "ComicCollectionQueryBuilderFactory is created"() {
		when:
		comicCollectionQueryBuilderFactory = new ComicCollectionQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		comicCollectionQueryBuilderFactory != null
	}

}
