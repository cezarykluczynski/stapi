package com.cezarykluczynski.stapi.server.common.cache

import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable
import org.springframework.core.env.Environment
import spock.lang.Specification

class EagerCachingDeciderTest extends Specification {

	Environment environmentMock

	EagerCachingDecider eagerCachingDecider

	void setup() {
		environmentMock = Mock()
	}

	void "when canonical domain is stapi.co, eager caching is enabled"() {
		given:
		environmentMock.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN) >> 'stapi.co'
		environmentMock.getProperty(EnvironmentVariable.STAPI_EAGER_CACHING) >> null

		when:
		eagerCachingDecider = new EagerCachingDecider(environmentMock, false)

		then:
		eagerCachingDecider.eagerCachingEnabled
	}

	void "when dedicated env variable is set, eager caching is enabled"() {
		given:
		environmentMock.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN) >> null
		environmentMock.getProperty(EnvironmentVariable.STAPI_EAGER_CACHING) >> 'true'

		when:
		eagerCachingDecider = new EagerCachingDecider(environmentMock, false)

		then:
		eagerCachingDecider.eagerCachingEnabled
	}

	void "when property is set, eager caching is enabled"() {
		given:
		environmentMock.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN) >> 'st.api.com'
		environmentMock.getProperty(EnvironmentVariable.STAPI_EAGER_CACHING) >> 'false'

		when:
		eagerCachingDecider = new EagerCachingDecider(environmentMock, true)

		then:
		eagerCachingDecider.eagerCachingEnabled
	}

	void "when no condition is met, eager caching is disabled"() {
		given:
		environmentMock.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN) >> 'st.api.com'
		environmentMock.getProperty(EnvironmentVariable.STAPI_EAGER_CACHING) >> ''

		when:
		eagerCachingDecider = new EagerCachingDecider(environmentMock, false)

		then:
		!eagerCachingDecider.eagerCachingEnabled
	}

}
