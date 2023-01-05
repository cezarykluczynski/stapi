package com.cezarykluczynski.stapi.model.magazine_series.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MagazineSeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private MagazineSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "MagazineSeriesQueryBuilderFactory is created"() {
		when:
		comicSeriesQueryBuilderFactory = new MagazineSeriesQueryBuilderFactory(jpaContextMock)

		then:
		comicSeriesQueryBuilderFactory != null
	}

}
