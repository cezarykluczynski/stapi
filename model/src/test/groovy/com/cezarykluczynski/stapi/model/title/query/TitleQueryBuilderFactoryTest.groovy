package com.cezarykluczynski.stapi.model.title.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TitleQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private TitleQueryBuilderFactory titleQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "TitleQueryBuilderFactory is created"() {
		when:
		titleQueryBuilderFactory = new TitleQueryBuilderFactory(jpaContextMock)

		then:
		titleQueryBuilderFactory != null
	}

}
