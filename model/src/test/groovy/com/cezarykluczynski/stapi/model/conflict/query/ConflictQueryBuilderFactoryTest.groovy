package com.cezarykluczynski.stapi.model.conflict.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ConflictQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ConflictQueryBuilderFactory conflictQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "ConflictQueryBuilderFactory is created"() {
		when:
		conflictQueryBuilderFactory = new ConflictQueryBuilderFactory(jpaContextMock)

		then:
		conflictQueryBuilderFactory != null
	}

}
