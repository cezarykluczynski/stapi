package com.cezarykluczynski.stapi.model.comics.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicsQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ComicsQueryBuilderFactory comicsQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "ComicsQueryBuilderFactory is created"() {
		when:
		comicsQueryBuilderFactory = new ComicsQueryBuilderFactory(jpaContextMock)

		then:
		comicsQueryBuilderFactory != null
	}

}
