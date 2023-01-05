package com.cezarykluczynski.stapi.model.book_series.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class BookSeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private BookSeriesQueryBuilderFactory bookSeriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "BookSeriesQueryBuilderFactory is created"() {
		when:
		bookSeriesQueryBuilderFactory = new BookSeriesQueryBuilderFactory(jpaContextMock)

		then:
		bookSeriesQueryBuilderFactory != null
	}

}
