package com.cezarykluczynski.stapi.model.company.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CompanyQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CompanyQueryBuilderFactory companyQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "CompanyQueryBuilder is created"() {
		when:
		companyQueryBuilerFactory = new CompanyQueryBuilderFactory(jpaContextMock)

		then:
		companyQueryBuilerFactory != null
	}

}
