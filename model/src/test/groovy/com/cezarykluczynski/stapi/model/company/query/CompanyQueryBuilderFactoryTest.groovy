package com.cezarykluczynski.stapi.model.company.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CompanyQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CompanyQueryBuilderFactory companyQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "CompanyQueryBuilderFactory is created"() {
		when:
		companyQueryBuilderFactory = new CompanyQueryBuilderFactory(jpaContextMock)

		then:
		companyQueryBuilderFactory != null
	}

}
