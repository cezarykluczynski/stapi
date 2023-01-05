package com.cezarykluczynski.stapi.model.technology.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TechnologyQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private TechnologyQueryBuilderFactory technologyQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "TechnologyQueryBuilderFactory is created"() {
		when:
		technologyQueryBuilderFactory = new TechnologyQueryBuilderFactory(jpaContextMock)

		then:
		technologyQueryBuilderFactory != null
	}

}
