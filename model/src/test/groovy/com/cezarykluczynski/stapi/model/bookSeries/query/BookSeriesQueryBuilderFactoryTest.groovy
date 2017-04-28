package com.cezarykluczynski.stapi.model.bookSeries.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class BookSeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private BookSeriesQueryBuilderFactory bookSeriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "BookSeriesQueryBuilder is created"() {
		when:
		bookSeriesQueryBuilderFactory = new BookSeriesQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		bookSeriesQueryBuilderFactory != null
	}

}
