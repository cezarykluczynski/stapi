package com.cezarykluczynski.stapi.model.occupation.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class OccupationQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private OccupationQueryBuilderFactory occupationQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "OccupationQueryBuilderFactory is created"() {
		when:
		occupationQueryBuilderFactory = new OccupationQueryBuilderFactory(jpaContextMock)

		then:
		occupationQueryBuilderFactory != null
	}

}
