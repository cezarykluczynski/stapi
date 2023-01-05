package com.cezarykluczynski.stapi.model.episode.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class EpisodeQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private EpisodeQueryBuilderFactory episodeQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "EpisodeQueryBuilderFactory is created"() {
		when:
		episodeQueryBuilderFactory = new EpisodeQueryBuilderFactory(jpaContextMock)

		then:
		episodeQueryBuilderFactory != null
	}

}
