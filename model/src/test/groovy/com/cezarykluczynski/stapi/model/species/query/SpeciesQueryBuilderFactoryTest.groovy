package com.cezarykluczynski.stapi.model.species.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SpeciesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private SpeciesQueryBuilderFactory speciesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "SpeciesQueryBuilder is created"() {
		when:
		speciesQueryBuilderFactory = new SpeciesQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		speciesQueryBuilderFactory != null
	}

}
