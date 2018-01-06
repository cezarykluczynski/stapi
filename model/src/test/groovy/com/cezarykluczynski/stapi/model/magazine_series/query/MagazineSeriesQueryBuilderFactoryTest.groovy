package com.cezarykluczynski.stapi.model.magazine_series.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MagazineSeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private MagazineSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "MagazineSeriesQueryBuilderFactory is created"() {
		when:
		comicSeriesQueryBuilderFactory = new MagazineSeriesQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		comicSeriesQueryBuilderFactory != null
	}

}
