package com.cezarykluczynski.stapi.model.video_game.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class VideoGameQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private VideoGameQueryBuilderFactory videoGameQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "VideoGameQueryBuilderFactory is created"() {
		when:
		videoGameQueryBuilderFactory = new VideoGameQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		videoGameQueryBuilderFactory != null
	}

}
