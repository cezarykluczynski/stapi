package com.cezarykluczynski.stapi.model.performer.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class PerformerQueryBuilerTest extends Specification {

	private JpaContext jpaContextMock

	private PerformerQueryBuiler performerQueryBuiler

	def setup() {
		jpaContextMock = Mock(JpaContext)
	}

	def "PerformerQueryBuilder is created"() {
		when:
		performerQueryBuiler = new PerformerQueryBuiler(jpaContextMock)

		then:
		performerQueryBuiler != null
	}

}
