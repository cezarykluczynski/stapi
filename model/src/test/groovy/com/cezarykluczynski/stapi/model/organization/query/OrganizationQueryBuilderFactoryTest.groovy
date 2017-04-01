package com.cezarykluczynski.stapi.model.organization.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class OrganizationQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private OrganizationQueryBuilderFactory organizationQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "OrganizationQueryBuilder is created"() {
		when:
		organizationQueryBuilerFactory = new OrganizationQueryBuilderFactory(jpaContextMock)

		then:
		organizationQueryBuilerFactory != null
	}

}
