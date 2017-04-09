package com.cezarykluczynski.stapi.model.comicSeries.query

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

	void "ComicSeriesQueryBuilder is created"() {
		when:
		comicSeriesQueryBuilderFactory = new ComicSeriesQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		comicSeriesQueryBuilderFactory != null
	}

}
