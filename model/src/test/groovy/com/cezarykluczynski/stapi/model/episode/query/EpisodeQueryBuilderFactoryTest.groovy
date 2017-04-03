package com.cezarykluczynski.stapi.model.episode.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class EpisodeQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private EpisodeQueryBuilderFactory episodeQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "EpisodeQueryBuilder is created"() {
		when:
		episodeQueryBuilderFactory = new EpisodeQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		episodeQueryBuilderFactory != null
	}

}
