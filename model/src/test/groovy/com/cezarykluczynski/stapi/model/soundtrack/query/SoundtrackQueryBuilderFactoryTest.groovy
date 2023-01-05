package com.cezarykluczynski.stapi.model.soundtrack.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SoundtrackQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private SoundtrackQueryBuilderFactory soundtrackQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "SoundtrackQueryBuilderFactory is created"() {
		when:
		soundtrackQueryBuilderFactory = new SoundtrackQueryBuilderFactory(jpaContextMock)

		then:
		soundtrackQueryBuilderFactory != null
	}

}
