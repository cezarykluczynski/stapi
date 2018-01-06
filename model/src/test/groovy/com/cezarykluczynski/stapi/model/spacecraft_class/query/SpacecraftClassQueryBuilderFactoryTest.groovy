package com.cezarykluczynski.stapi.model.spacecraft_class.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SpacecraftClassQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private SpacecraftClassQueryBuilderFactory spacecraftClassQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "SpacecraftClassQueryBuilderFactory is created"() {
		when:
		spacecraftClassQueryBuilderFactory = new SpacecraftClassQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		spacecraftClassQueryBuilderFactory != null
	}

}
