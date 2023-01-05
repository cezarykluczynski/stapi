package com.cezarykluczynski.stapi.model.book_collection.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class BookCollectionQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private BookCollectionQueryBuilderFactory bookCollectionQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "BookCollectionQueryBuilderFactory is created"() {
		when:
		bookCollectionQueryBuilderFactory = new BookCollectionQueryBuilderFactory(jpaContextMock)

		then:
		bookCollectionQueryBuilderFactory != null
	}

}
