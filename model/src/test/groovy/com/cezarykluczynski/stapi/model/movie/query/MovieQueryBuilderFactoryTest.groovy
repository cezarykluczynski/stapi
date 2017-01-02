package com.cezarykluczynski.stapi.model.movie.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MovieQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private MovieQueryBuilderFactory movieQueryBuilerFactory

	def setup() {
		jpaContextMock = Mock(JpaContext)
	}

	def "MovieQueryBuilder is created"() {
		when:
		movieQueryBuilerFactory = new MovieQueryBuilderFactory(jpaContextMock)

		then:
		movieQueryBuilerFactory != null
	}

}
