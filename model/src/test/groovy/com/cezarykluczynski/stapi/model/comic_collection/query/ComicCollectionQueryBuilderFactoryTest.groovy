package com.cezarykluczynski.stapi.model.comic_collection.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicCollectionQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ComicCollectionQueryBuilderFactory comicCollectionQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "ComicCollectionQueryBuilderFactory is created"() {
		when:
		comicCollectionQueryBuilderFactory = new ComicCollectionQueryBuilderFactory(jpaContextMock)

		then:
		comicCollectionQueryBuilderFactory != null
	}

}
