package com.cezarykluczynski.stapi.model.comicStrip.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
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

	void "ComicStripQueryBuilder is created"() {
		when:
		comicStripQueryBuilderFactory = new ComicStripQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		comicStripQueryBuilderFactory != null
	}

}
