package com.cezarykluczynski.stapi.model.episode.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class EpisodeQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private EpisodeQueryBuilderFactory episodeQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "EpisodeQueryBuilder is created"() {
		when:
		episodeQueryBuilerFactory = new EpisodeQueryBuilderFactory(jpaContextMock)

		then:
		episodeQueryBuilerFactory != null
	}

}
