package com.cezarykluczynski.stapi.model.common.cache

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import spock.lang.Specification

class CacheAllCachingStrategyTest extends Specification {

	private CacheAllCachingStrategy cacheAllCachingStrategy

	void setup() {
		cacheAllCachingStrategy = new CacheAllCachingStrategy()
	}

	void "marks everything as cacheable"() {
		given:
		QueryBuilder queryBuilder = Mock()

		when:
		boolean cacheable = cacheAllCachingStrategy.isCacheable(queryBuilder)

		then:
		cacheable
	}

}
