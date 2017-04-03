package com.cezarykluczynski.stapi.model.company.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CompanyQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private CompanyQueryBuilderFactory companyQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "CompanyQueryBuilder is created"() {
		when:
		companyQueryBuilderFactory = new CompanyQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		companyQueryBuilderFactory != null
	}

}
