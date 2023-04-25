package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import spock.lang.Specification
import spock.lang.Unroll

class EntityLookupByNameCacheServiceTest extends Specification {

	EntityLookupByNameCacheService entityLookupByNameCacheService

	void setup() {
		entityLookupByNameCacheService = new EntityLookupByNameCacheService()
	}

	@Unroll('when #entityType, #pageTitle and #mediaWikiSource is passed, #key is returned')
	void "when key components are passed, cache key is returned"() {
		expect:
		entityLookupByNameCacheService.resolveKey(entityType, pageTitle, mediaWikiSource) == key

		where:
		entityType  | pageTitle       | mediaWikiSource                 | key
		'performer' | 'Title#Section' | MediaWikiSource.MEMORY_ALPHA_EN | 'performer:Title:MEMORY_ALPHA_EN'
		'character' | 'Picard'        | MediaWikiSource.MEMORY_ALPHA_EN | 'character:Picard:MEMORY_ALPHA_EN'
	}

}
