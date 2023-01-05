package com.cezarykluczynski.stapi.model.video_release.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class VideoReleaseQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private VideoReleaseQueryBuilderFactory videoReleaseQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "VideoReleaseQueryBuilderFactory is created"() {
		when:
		videoReleaseQueryBuilderFactory = new VideoReleaseQueryBuilderFactory(jpaContextMock)

		then:
		videoReleaseQueryBuilderFactory != null
	}

}
