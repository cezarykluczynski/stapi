package com.cezarykluczynski.stapi.etl.mediawiki.cache

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
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

	void "marks title a cacheable when it was requested more than 2 times"() {
		when: 'title 1 with source 1 is asked for 1 time'
		boolean cacheableTitle1Source1After1Hit = frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_1)

		then: 'title 1 with source 1 it is still not marked as cacheable'
		!cacheableTitle1Source1After1Hit

		when: 'title 1 with source 1 is asked for the second time'
		boolean cacheableTitle1Source1After2Hits = frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_1)

		then: 'title 1 with source 1 is marked as cacheable'
		cacheableTitle1Source1After2Hits

		when: 'title 1 with source 2 is asked for 1 time'
		boolean cacheableTitle1Source2After1Hit = frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_2)

		then: 'title 1 with source 2 it is still not marked as cacheable'
		!cacheableTitle1Source2After1Hit

		when: 'title 1 with source 2 is asked for the second time'
		boolean cacheableTitle1Source2After2Hits = frequentHitCachingHelper.isCacheable(TITLE_1, SOURCE_2)

		then: 'title 1 with source 2 is marked as cacheable'
		cacheableTitle1Source2After2Hits

		when: 'title 2 with source 1 is asked for 1 time'
		boolean cacheableTitle2Source1After1Hit = frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_1)

		then: 'title 2 with source 1 it is still not marked as cacheable'
		!cacheableTitle2Source1After1Hit

		when: 'title 2 with source 1 is asked for the second time'
		boolean cacheableTitle2Source1After2Hits = frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_1)

		then: 'title 2 with source 1 is marked as cacheable'
		cacheableTitle2Source1After2Hits

		when: 'title 2 with source 2 is asked for 1 time'
		boolean cacheableTitle2Source2After4Hits = frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_2)

		then: 'title 2 with source 2 it is still not marked as cacheable'
		!cacheableTitle2Source2After4Hits

		when: 'title 2 with source 2 is asked for the second time'
		boolean cacheableTitle2Source2After2Hits = frequentHitCachingHelper.isCacheable(TITLE_2, SOURCE_2)

		then: 'title 2 with source 2 is marked as cacheable'
		cacheableTitle2Source2After2Hits

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
