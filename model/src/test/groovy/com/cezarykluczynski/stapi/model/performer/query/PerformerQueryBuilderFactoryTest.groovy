package com.cezarykluczynski.stapi.model.performer.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class PerformerQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private PerformerQueryBuilderFactory performerQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "PerformerQueryBuilderFactory is created"() {
		when:
		performerQueryBuilderFactory = new PerformerQueryBuilderFactory(jpaContextMock)

		then:
		performerQueryBuilderFactory != null
	}

}
