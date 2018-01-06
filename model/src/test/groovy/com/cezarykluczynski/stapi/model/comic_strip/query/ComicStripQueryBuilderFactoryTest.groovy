package com.cezarykluczynski.stapi.model.comic_strip.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicStripQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private ComicStripQueryBuilderFactory comicStripQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "ComicStripQueryBuilderFactory is created"() {
		when:
		comicStripQueryBuilderFactory = new ComicStripQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		comicStripQueryBuilderFactory != null
	}

}
