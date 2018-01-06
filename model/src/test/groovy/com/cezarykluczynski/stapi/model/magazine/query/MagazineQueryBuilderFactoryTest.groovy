package com.cezarykluczynski.stapi.model.magazine.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class MagazineQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private MagazineQueryBuilderFactory magazineQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "MagazineQueryBuilderFactory is created"() {
		when:
		magazineQueryBuilderFactory = new MagazineQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		magazineQueryBuilderFactory != null
	}

}
