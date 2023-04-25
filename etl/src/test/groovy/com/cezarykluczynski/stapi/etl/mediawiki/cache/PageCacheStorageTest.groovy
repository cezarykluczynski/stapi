package com.cezarykluczynski.stapi.etl.mediawiki.cache

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class PageCacheStorageTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageCacheStorage pageCacheStorage

	void setup() {
		pageCacheStorage = new PageCacheStorage()
	}

	void "does not allow putting null into cache"() {
		when:
		pageCacheStorage.put(null)

		then:
		thrown(NullPointerException)
	}

	void "does not allow pages that are effect of redirect to be put into cache"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		pageCacheStorage.put(page)

		then:
		thrown(IllegalArgumentException)
	}

	void "allows putting page, then retrieving it"() {
		given:
		Page page = new Page(
				title: TITLE,
				mediaWikiSource: MEDIA_WIKI_SOURCE)
		pageCacheStorage.put(page)

		when:
		Page pageOutput = pageCacheStorage.get(TITLE, MEDIA_WIKI_SOURCE)

		then:
		pageOutput == page
	}

	void "returns null for not found page"() {
		when:
		Page page = pageCacheStorage.get(TITLE, MEDIA_WIKI_SOURCE)

		then:
		page == null
	}

}
