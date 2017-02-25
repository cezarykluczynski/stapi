package com.cezarykluczynski.stapi.model.comicCollection.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicCollectionQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ComicCollectionQueryBuilderFactory comicCollectionQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "ComicCollectionQueryBuilder is created"() {
		when:
		comicCollectionQueryBuilerFactory = new ComicCollectionQueryBuilderFactory(jpaContextMock)

		then:
		comicCollectionQueryBuilerFactory != null
	}

}
