package com.cezarykluczynski.stapi.model.comics.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicsQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ComicsQueryBuilderFactory comicsQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "ComicsQueryBuilder is created"() {
		when:
		comicsQueryBuilerFactory = new ComicsQueryBuilderFactory(jpaContextMock)

		then:
		comicsQueryBuilerFactory != null
	}

}
