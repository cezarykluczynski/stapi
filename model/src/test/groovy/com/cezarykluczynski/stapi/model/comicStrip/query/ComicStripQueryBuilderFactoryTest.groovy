package com.cezarykluczynski.stapi.model.comicStrip.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicStripQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ComicStripQueryBuilderFactory comicStripQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "ComicStripQueryBuilder is created"() {
		when:
		comicStripQueryBuilerFactory = new ComicStripQueryBuilderFactory(jpaContextMock)

		then:
		comicStripQueryBuilerFactory != null
	}

}
