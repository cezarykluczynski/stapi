package com.cezarykluczynski.stapi.sources.mediawiki.cache

import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.CacheablePageNames
import spock.lang.Specification

class PageCacheServiceTest extends Specification {

	private static final String TITLE = "Title"
	private static final String SECTION = "#Section"
	private static final String TITLE_WITH_SECTION = TITLE + SECTION

	private PageCacheService pageCacheService

	def setup() {
		pageCacheService = new PageCacheService()
	}

	def "tells when page is cacheable"() {
		when:
		boolean result = pageCacheService.isCacheable(CacheablePageNames.TITLES.get(0))

		then:
		result
	}

	def "tells when page is cacheable despite redirecting to section"() {
		when:
		boolean result = pageCacheService.isCacheable(CacheablePageNames.TITLES.get(0) + SECTION)

		then:
		result
	}

	def "tells when page is not cacheable"() {
		when:
		boolean result = pageCacheService.isCacheable(TITLE)

		then:
		!result
	}

	def "resolve title without section to original value"() {
		when:
		String result = pageCacheService.resolveKey(TITLE)

		then:
		result == TITLE
	}

	def "resolve title with section to title without section"() {
		when:
		String result = pageCacheService.resolveKey(TITLE_WITH_SECTION)

		then:
		result == TITLE
	}

}
