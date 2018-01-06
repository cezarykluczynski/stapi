package com.cezarykluczynski.stapi.model.series.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private SeriesQueryBuilderFactory seriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "SeriesQueryBuilderFactory is created"() {
		when:
		seriesQueryBuilderFactory = new SeriesQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		seriesQueryBuilderFactory != null
	}

}
