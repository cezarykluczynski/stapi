package com.cezarykluczynski.stapi.model.episode.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class EpisodeQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private EpisodeQueryBuilderFactory episodeQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "EpisodeQueryBuilderFactory is created"() {
		when:
		episodeQueryBuilderFactory = new EpisodeQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		episodeQueryBuilderFactory != null
	}

}
