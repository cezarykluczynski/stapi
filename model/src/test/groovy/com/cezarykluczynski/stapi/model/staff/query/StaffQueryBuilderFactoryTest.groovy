package com.cezarykluczynski.stapi.model.staff.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class StaffQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private StaffQueryBuilderFactory staffQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "StaffQueryBuilder is created"() {
		when:
		staffQueryBuilderFactory = new StaffQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		staffQueryBuilderFactory != null
	}

}
