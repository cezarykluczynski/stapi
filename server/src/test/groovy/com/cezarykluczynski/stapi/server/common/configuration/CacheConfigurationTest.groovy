package com.cezarykluczynski.stapi.server.common.configuration

import org.springframework.cache.CacheManager
import spock.lang.Specification

class CacheConfigurationTest extends Specification {

	CacheConfiguration cacheConfiguration

	void setup() {
		cacheConfiguration = new CacheConfiguration()
	}

	void "cache manager is created with the right caches"() {
		when:
		CacheManager cacheManager = cacheConfiguration.cacheManager()

		then:
		cacheManager.cacheNames.size() == 3
		cacheManager.cacheNames.containsAll(['pagesCache', 'entitiesCache', 'entityLookupByNameCache'])
	}

}
