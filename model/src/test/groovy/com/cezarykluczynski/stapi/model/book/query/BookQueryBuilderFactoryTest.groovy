package com.cezarykluczynski.stapi.model.book.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class BookQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private BookQueryBuilderFactory bookQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "BookQueryBuilderFactory is created"() {
		when:
		bookQueryBuilderFactory = new BookQueryBuilderFactory(jpaContextMock)

		then:
		bookQueryBuilderFactory != null
	}

}
