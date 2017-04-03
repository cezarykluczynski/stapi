package com.cezarykluczynski.stapi.model.movie.query

import com.cezarykluczynski.stapi.model.common.query.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MovieQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private MovieQueryBuilderFactory movieQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		cachingStrategyMock = Mock(CachingStrategy)
	}

	void "MovieQueryBuilder is created"() {
		when:
		movieQueryBuilderFactory = new MovieQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		movieQueryBuilderFactory != null
	}

}
