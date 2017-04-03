package com.cezarykluczynski.stapi.model.comicSeries.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicSeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private ComicSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "ComicSeriesQueryBuilder is created"() {
		when:
		comicSeriesQueryBuilderFactory = new ComicSeriesQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		comicSeriesQueryBuilderFactory != null
	}

}
