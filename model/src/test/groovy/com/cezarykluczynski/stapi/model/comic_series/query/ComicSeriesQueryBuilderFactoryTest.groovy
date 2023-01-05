package com.cezarykluczynski.stapi.model.comic_series.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ComicSeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ComicSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "ComicSeriesQueryBuilderFactory is created"() {
		when:
		comicSeriesQueryBuilderFactory = new ComicSeriesQueryBuilderFactory(jpaContextMock)

		then:
		comicSeriesQueryBuilderFactory != null
	}

}
