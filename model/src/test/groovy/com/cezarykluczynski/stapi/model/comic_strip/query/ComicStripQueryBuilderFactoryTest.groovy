package com.cezarykluczynski.stapi.model.comic_strip.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicStripQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ComicStripQueryBuilderFactory comicStripQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "ComicStripQueryBuilderFactory is created"() {
		when:
		comicStripQueryBuilderFactory = new ComicStripQueryBuilderFactory(jpaContextMock)

		then:
		comicStripQueryBuilderFactory != null
	}

}
