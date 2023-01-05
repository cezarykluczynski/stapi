package com.cezarykluczynski.stapi.model.staff.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class StaffQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private StaffQueryBuilderFactory staffQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "StaffQueryBuilderFactory is created"() {
		when:
		staffQueryBuilderFactory = new StaffQueryBuilderFactory(jpaContextMock)

		then:
		staffQueryBuilderFactory != null
	}

}
