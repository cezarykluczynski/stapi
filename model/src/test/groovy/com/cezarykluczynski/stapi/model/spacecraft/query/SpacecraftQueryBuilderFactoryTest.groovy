package com.cezarykluczynski.stapi.model.spacecraft.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SpacecraftQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private SpacecraftQueryBuilderFactory spacecraftQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "SpacecraftQueryBuilderFactory is created"() {
		when:
		spacecraftQueryBuilderFactory = new SpacecraftQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		spacecraftQueryBuilderFactory != null
	}

}
