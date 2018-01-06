package com.cezarykluczynski.stapi.model.soundtrack.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SoundtrackQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private SoundtrackQueryBuilderFactory soundtrackQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "SoundtrackQueryBuilderFactory is created"() {
		when:
		soundtrackQueryBuilderFactory = new SoundtrackQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		soundtrackQueryBuilderFactory != null
	}

}
