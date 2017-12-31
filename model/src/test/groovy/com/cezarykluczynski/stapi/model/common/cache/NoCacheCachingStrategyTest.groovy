package com.cezarykluczynski.stapi.model.common.cache

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import spock.lang.Specification

class NoCacheCachingStrategyTest extends Specification {

	private NoCacheCachingStrategy noCacheCachingStrategy

	void setup() {
		noCacheCachingStrategy = new NoCacheCachingStrategy()
	}

	void "marks everything as not cacheable"() {
		given:
		QueryBuilder queryBuilder = Mock()

		when:
		boolean cacheable = noCacheCachingStrategy.isCacheable(queryBuilder)

		then:
		!cacheable
	}

}
