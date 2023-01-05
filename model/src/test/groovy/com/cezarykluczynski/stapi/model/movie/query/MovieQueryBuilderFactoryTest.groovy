package com.cezarykluczynski.stapi.model.movie.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MovieQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private MovieQueryBuilderFactory movieQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "MovieQueryBuilderFactory is created"() {
		when:
		movieQueryBuilderFactory = new MovieQueryBuilderFactory(jpaContextMock)

		then:
		movieQueryBuilderFactory != null
	}

}
