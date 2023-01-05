package com.cezarykluczynski.stapi.model.spacecraft_class.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SpacecraftClassQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private SpacecraftClassQueryBuilderFactory spacecraftClassQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "SpacecraftClassQueryBuilderFactory is created"() {
		when:
		spacecraftClassQueryBuilderFactory = new SpacecraftClassQueryBuilderFactory(jpaContextMock)

		then:
		spacecraftClassQueryBuilderFactory != null
	}

}
