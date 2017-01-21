package com.cezarykluczynski.stapi.sources.configuration

import org.springframework.cache.CacheManager
import spock.lang.Specification

class SourcesConfigurationTest extends Specification {

	private SourcesConfiguration sourcesConfiguration

	void setup() {
		sourcesConfiguration = new SourcesConfiguration()
	}

	void "creates cache manager"() {
		when:
		CacheManager cacheManager = sourcesConfiguration.cacheManager()

		then:
		cacheManager.cacheNames.size() == 1
		cacheManager.getCache('pagesCache') != null
	}

}
