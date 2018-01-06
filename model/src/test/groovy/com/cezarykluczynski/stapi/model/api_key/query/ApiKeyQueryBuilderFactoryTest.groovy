package com.cezarykluczynski.stapi.model.api_key.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class ApiKeyQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "ApiKeyQueryBuilderFactory is created"() {
		when:
		apiKeyQueryBuilderFactory = new ApiKeyQueryBuilderFactory(jpaContextMock)

		then:
		apiKeyQueryBuilderFactory != null
	}

}
