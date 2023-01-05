package com.cezarykluczynski.stapi.model.video_game.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class VideoGameQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private VideoGameQueryBuilderFactory videoGameQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "VideoGameQueryBuilderFactory is created"() {
		when:
		videoGameQueryBuilderFactory = new VideoGameQueryBuilderFactory(jpaContextMock)

		then:
		videoGameQueryBuilderFactory != null
	}

}
