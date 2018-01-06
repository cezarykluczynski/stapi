package com.cezarykluczynski.stapi.model.company.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class CompanyQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private CompanyQueryBuilderFactory companyQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "CompanyQueryBuilderFactory is created"() {
		when:
		companyQueryBuilderFactory = new CompanyQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		companyQueryBuilderFactory != null
	}

}
