package com.cezarykluczynski.stapi.model.element.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ElementQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ElementQueryBuilderFactory elementQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "ElementQueryBuilderFactory is created"() {
		when:
		elementQueryBuilderFactory = new ElementQueryBuilderFactory(jpaContextMock)

		then:
		elementQueryBuilderFactory != null
	}

}
