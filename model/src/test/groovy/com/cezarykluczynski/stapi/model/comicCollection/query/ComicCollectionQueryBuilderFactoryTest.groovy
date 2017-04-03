package com.cezarykluczynski.stapi.model.comicCollection.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicCollectionQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private ComicCollectionQueryBuilderFactory comicCollectionQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "ComicCollectionQueryBuilder is created"() {
		when:
		comicCollectionQueryBuilderFactory = new ComicCollectionQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		comicCollectionQueryBuilderFactory != null
	}

}
