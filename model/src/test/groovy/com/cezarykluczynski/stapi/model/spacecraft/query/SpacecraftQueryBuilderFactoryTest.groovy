package com.cezarykluczynski.stapi.model.spacecraft.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class SpacecraftQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private SpacecraftQueryBuilderFactory spacecraftQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "SpacecraftQueryBuilderFactory is created"() {
		when:
		spacecraftQueryBuilderFactory = new SpacecraftQueryBuilderFactory(jpaContextMock)

		then:
		spacecraftQueryBuilderFactory != null
	}

}
