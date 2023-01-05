package com.cezarykluczynski.stapi.model.organization.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class OrganizationQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private OrganizationQueryBuilderFactory organizationQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "OrganizationQueryBuilderFactory is created"() {
		when:
		organizationQueryBuilderFactory = new OrganizationQueryBuilderFactory(jpaContextMock)

		then:
		organizationQueryBuilderFactory != null
	}

}
