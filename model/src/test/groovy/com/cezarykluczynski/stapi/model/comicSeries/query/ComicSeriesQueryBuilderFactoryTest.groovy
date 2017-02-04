package com.cezarykluczynski.stapi.model.comicSeries.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicSeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ComicSeriesQueryBuilderFactory comicSeriesQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "ComicSeriesQueryBuilder is created"() {
		when:
		comicSeriesQueryBuilerFactory = new ComicSeriesQueryBuilderFactory(jpaContextMock)

		then:
		comicSeriesQueryBuilerFactory != null
	}

}
