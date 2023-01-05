package com.cezarykluczynski.stapi.model.magazine.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MagazineQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private MagazineQueryBuilderFactory magazineQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "MagazineQueryBuilderFactory is created"() {
		when:
		magazineQueryBuilderFactory = new MagazineQueryBuilderFactory(jpaContextMock)

		then:
		magazineQueryBuilderFactory != null
	}

}
