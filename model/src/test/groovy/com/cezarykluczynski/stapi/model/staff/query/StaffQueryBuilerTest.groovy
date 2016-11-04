package com.cezarykluczynski.stapi.model.staff.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class StaffQueryBuilerTest extends Specification {

	private JpaContext jpaContextMock

	private StaffQueryBuiler staffQueryBuiler

	def setup() {
		jpaContextMock = Mock(JpaContext)
	}

	def "StaffQueryBuiler is created"() {
		when:
		staffQueryBuiler = new StaffQueryBuiler(jpaContextMock)

		then:
		staffQueryBuiler != null
	}

}
