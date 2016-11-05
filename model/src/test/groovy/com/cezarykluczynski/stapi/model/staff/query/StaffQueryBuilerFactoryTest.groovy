package com.cezarykluczynski.stapi.model.staff.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class StaffQueryBuilerFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private StaffQueryBuilerFactory staffQueryBuilerFactory

	def setup() {
		jpaContextMock = Mock(JpaContext)
	}

	def "StaffQueryBuiler is created"() {
		when:
		staffQueryBuilerFactory = new StaffQueryBuilerFactory(jpaContextMock)

		then:
		staffQueryBuilerFactory != null
	}

}
