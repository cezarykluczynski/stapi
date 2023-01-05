package com.cezarykluczynski.stapi.model.season.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SeasonQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private SeasonQueryBuilderFactory seasonQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "SeasonQueryBuilderFactory is created"() {
		when:
		seasonQueryBuilderFactory = new SeasonQueryBuilderFactory(jpaContextMock)

		then:
		seasonQueryBuilderFactory != null
	}

}
