package com.cezarykluczynski.stapi.model.material.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MaterialQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private MaterialQueryBuilderFactory materialQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "MaterialQueryBuilderFactory is created"() {
		when:
		materialQueryBuilderFactory = new MaterialQueryBuilderFactory(jpaContextMock)

		then:
		materialQueryBuilderFactory != null
	}

}
