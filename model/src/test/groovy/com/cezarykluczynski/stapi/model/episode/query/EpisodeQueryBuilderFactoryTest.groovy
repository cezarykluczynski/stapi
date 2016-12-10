package com.cezarykluczynski.stapi.model.episode.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class EpisodeQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private EpisodeQueryBuilderFactory episodeQueryBuilerFactory

	def setup() {
		jpaContextMock = Mock(JpaContext)
	}

	def "EpisodeQueryBuilder is created"() {
		when:
		episodeQueryBuilerFactory = new EpisodeQueryBuilderFactory(jpaContextMock)

		then:
		episodeQueryBuilerFactory != null
	}

}
