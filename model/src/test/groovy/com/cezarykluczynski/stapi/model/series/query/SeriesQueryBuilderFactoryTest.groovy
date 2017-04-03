package com.cezarykluczynski.stapi.model.series.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private SeriesQueryBuilderFactory seriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "SeriesQueryBuilder is created"() {
		when:
		seriesQueryBuilderFactory = new SeriesQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		seriesQueryBuilderFactory != null
	}

}
