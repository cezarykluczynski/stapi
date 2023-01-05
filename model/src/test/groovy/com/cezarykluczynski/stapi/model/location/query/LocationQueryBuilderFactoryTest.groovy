package com.cezarykluczynski.stapi.model.location.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class LocationQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private LocationQueryBuilderFactory locationQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "LocationQueryBuilderFactory is created"() {
		when:
		locationQueryBuilderFactory = new LocationQueryBuilderFactory(jpaContextMock)

		then:
		locationQueryBuilderFactory != null
	}

}
