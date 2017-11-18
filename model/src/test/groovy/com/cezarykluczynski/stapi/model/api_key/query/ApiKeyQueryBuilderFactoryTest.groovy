package com.cezarykluczynski.stapi.model.api_key.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ApiKeyQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "ApiKeyQueryBuilder is created"() {
		when:
		apiKeyQueryBuilderFactory = new ApiKeyQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		apiKeyQueryBuilderFactory != null
	}

}
