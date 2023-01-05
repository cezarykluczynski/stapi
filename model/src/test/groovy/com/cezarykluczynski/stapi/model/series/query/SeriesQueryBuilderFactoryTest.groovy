package com.cezarykluczynski.stapi.model.series.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private SeriesQueryBuilderFactory seriesQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "SeriesQueryBuilderFactory is created"() {
		when:
		seriesQueryBuilderFactory = new SeriesQueryBuilderFactory(jpaContextMock)

		then:
		seriesQueryBuilderFactory != null
	}

}
