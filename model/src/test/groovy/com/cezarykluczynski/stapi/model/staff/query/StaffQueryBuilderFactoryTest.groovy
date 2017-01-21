package com.cezarykluczynski.stapi.model.staff.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class StaffQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private StaffQueryBuilderFactory staffQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "StaffQueryBuiler is created"() {
		when:
		staffQueryBuilerFactory = new StaffQueryBuilderFactory(jpaContextMock)

		then:
		staffQueryBuilerFactory != null
	}

}
