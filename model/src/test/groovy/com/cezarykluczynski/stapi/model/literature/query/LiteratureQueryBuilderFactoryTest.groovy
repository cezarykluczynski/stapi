package com.cezarykluczynski.stapi.model.literature.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class LiteratureQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private LiteratureQueryBuilderFactory literatureQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "LiteratureQueryBuilderFactory is created"() {
		when:
		literatureQueryBuilderFactory = new LiteratureQueryBuilderFactory(jpaContextMock)

		then:
		literatureQueryBuilderFactory != null
	}

}
