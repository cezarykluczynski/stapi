package com.cezarykluczynski.stapi.model.comic_series.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicSeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private ComicSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "ComicSeriesQueryBuilderFactory is created"() {
		when:
		comicSeriesQueryBuilderFactory = new ComicSeriesQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		comicSeriesQueryBuilderFactory != null
	}

}
