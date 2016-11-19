package com.cezarykluczynski.stapi.sources.mediawiki.cache

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.CacheablePageNames
import spock.lang.Specification

class PageCacheServiceTest extends Specification {

	private static final String TITLE = "Title"
	private static final String SECTION = "#Section"
	private static final String TITLE_WITH_SECTION = TITLE + SECTION
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageCacheService pageCacheService

	def setup() {
		pageCacheService = new PageCacheService()
	}

	def "tells when page is cacheable"() {
		when:
		boolean result = pageCacheService.isCacheable(CacheablePageNames.SOURCES_TITLES.get(MEDIA_WIKI_SOURCE)[0], MEDIA_WIKI_SOURCE)

		then:
		result
	}

	def "tells when page is cacheable despite redirecting to section"() {
		when:
		boolean result = pageCacheService.isCacheable(CacheablePageNames.SOURCES_TITLES.get(MEDIA_WIKI_SOURCE)[0] + SECTION, MEDIA_WIKI_SOURCE)

		then:
		result
	}

	def "tells when page is not cacheable"() {
		when:
		boolean result = pageCacheService.isCacheable(TITLE, MEDIA_WIKI_SOURCE)

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
