package com.cezarykluczynski.stapi.model.series.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SeriesQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private SeriesQueryBuilderFactory seriesQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
	}

	void "SeriesQueryBuiler is created"() {
		when:
		seriesQueryBuilerFactory = new SeriesQueryBuilderFactory(jpaContextMock)

		then:
		seriesQueryBuilerFactory != null
	}

}
