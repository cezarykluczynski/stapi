package com.cezarykluczynski.stapi.model.organization.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class OrganizationQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private OrganizationQueryBuilderFactory organizationQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "OrganizationQueryBuilderFactory is created"() {
		when:
		organizationQueryBuilderFactory = new OrganizationQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		organizationQueryBuilderFactory != null
	}

}
