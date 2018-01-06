package com.cezarykluczynski.stapi.model.video_release.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class VideoReleaseQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private VideoReleaseQueryBuilderFactory videoReleaseQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "VideoReleaseQueryBuilderFactory is created"() {
		when:
		videoReleaseQueryBuilderFactory = new VideoReleaseQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		videoReleaseQueryBuilderFactory != null
	}

}
