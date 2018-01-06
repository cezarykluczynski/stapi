package com.cezarykluczynski.stapi.model.material.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MaterialQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private MaterialQueryBuilderFactory materialQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "MaterialQueryBuilderFactory is created"() {
		when:
		materialQueryBuilderFactory = new MaterialQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		materialQueryBuilderFactory != null
	}

}
