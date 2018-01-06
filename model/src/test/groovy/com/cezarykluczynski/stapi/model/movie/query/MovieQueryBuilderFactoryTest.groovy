package com.cezarykluczynski.stapi.model.movie.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MovieQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private MovieQueryBuilderFactory movieQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "MovieQueryBuilderFactory is created"() {
		when:
		movieQueryBuilderFactory = new MovieQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		movieQueryBuilderFactory != null
	}

}
