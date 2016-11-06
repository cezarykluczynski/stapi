package com.cezarykluczynski.stapi.model.performer.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class PerformerQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private PerformerQueryBuilderFactory performerQueryBuilerFactory

	def setup() {
		jpaContextMock = Mock(JpaContext)
	}

	def "PerformerQueryBuilder is created"() {
		when:
		performerQueryBuilerFactory = new PerformerQueryBuilderFactory(jpaContextMock)

		then:
		performerQueryBuilerFactory != null
	}

}
