package com.cezarykluczynski.stapi.model.performer.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class PerformerQueryBuilerFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private PerformerQueryBuilerFactory performerQueryBuilerFactory

	def setup() {
		jpaContextMock = Mock(JpaContext)
	}

	def "PerformerQueryBuilder is created"() {
		when:
		performerQueryBuilerFactory = new PerformerQueryBuilerFactory(jpaContextMock)

		then:
		performerQueryBuilerFactory != null
	}

}
