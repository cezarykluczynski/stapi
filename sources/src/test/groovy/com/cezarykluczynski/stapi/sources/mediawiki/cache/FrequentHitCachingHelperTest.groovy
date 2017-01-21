package com.cezarykluczynski.stapi.sources.mediawiki.cache

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import spock.lang.Specification

class FrequentHitCachingHelperTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final MediaWikiSource SOURCE_1 = MediaWikiSource.MEMORY_ALPHA_EN
	private static final MediaWikiSource SOURCE_2 = MediaWikiSource.MEMORY_BETA_EN

	private FrequentHitCachingHelper frequentHitCachingHelper

	void setup() {
		frequentHitCachingHelper = new FrequentHitCachingHelper()
	}

	void "marks title a cacheable when it was requested more than 5 times"() {
		when: 'title 1 with source 1 is asked for 4 times'
		frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_1)
		frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_1)
		frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_1)
		boolean cacheableTitle1Source1After4Hits = frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_1)

		then: 'title 1 with source 1 it is still not marked as cacheable'
		!cacheableTitle1Source1After4Hits

		when: 'title 1 with source 1 is asked for fifth time'
		boolean cacheableTitle1Source1After5Hits = frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_1)

		then: 'title 1 with source 1 is marked as cacheable'
		cacheableTitle1Source1After5Hits

		when: 'title 1 with source 2 is asked for 4 times'
		frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_2)
		frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_2)
		frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_2)
		boolean cacheableTitle1Source2After4Hits = frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_2)

		then: 'title 1 with source 2 it is still not marked as cacheable'
		!cacheableTitle1Source2After4Hits

		when: 'title 1 with source 2 is asked for fifth time'
		boolean cacheableTitle1Source2After5Hits = frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_2)

		then: 'title 1 with source 2 is marked as cacheable'
		cacheableTitle1Source2After5Hits

		when: 'title 2 with source 1 is asked for 4 times'
		frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_1)
		frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_1)
		frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_1)
		boolean cacheableTitle2Source1After4Hits = frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_1)

		then: 'title 2 with source 1 it is still not marked as cacheable'
		!cacheableTitle2Source1After4Hits

		when: 'title 2 with source 1 is asked for fifth time'
		boolean cacheableTitle2Source1After5Hits = frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_1)

		then: 'title 2 with source 1 is marked as cacheable'
		cacheableTitle2Source1After5Hits

		when: 'title 2 with source 2 is asked for 4 times'
		frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_2)
		frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_2)
		frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_2)
		boolean cacheableTitle2Source2After4Hits = frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_2)

		then: 'title 2 with source 2 it is still not marked as cacheable'
		!cacheableTitle2Source2After4Hits

		when: 'title 2 with source 2 is asked for fifth time'
		boolean cacheableTitle2Source2After5Hits = frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_2)

		then: 'title 2 with source 2 is marked as cacheable'
		cacheableTitle2Source2After5Hits

		when: 'dump is produced'
		Map<MediaWikiSource, Map<String, Integer>> dump = frequentHitCachingHelper.dumpStatisticsAndReset()

		then:
		dump[SOURCE_1][TITLE_1] == FrequentHitCachingHelper.CACHE_THRESHOLD
		dump[SOURCE_1][TITLE_2] == FrequentHitCachingHelper.CACHE_THRESHOLD
		dump[SOURCE_2][TITLE_1] == FrequentHitCachingHelper.CACHE_THRESHOLD
		dump[SOURCE_2][TITLE_2] == FrequentHitCachingHelper.CACHE_THRESHOLD

		then: 'titles are once again marked as not cacheable yet'
		!frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_1)
		!frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_2)
		!frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_1)
		!frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_2)
	}

}
