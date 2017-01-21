package com.cezarykluczynski.stapi.sources.mediawiki.cache

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Maps
import spock.lang.Specification

class FrequentHitCachingHelperDumpFormatterTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'
	private static final String TITLE_4 = 'TITLE_4'
	private static final String TITLE_5 = 'TITLE_5'
	private static final String TITLE_6 = 'TITLE_6'
	private static final Map<String, Integer> MEMORY_ALPHA_EN_MAP = Maps.newHashMap()
	private static final Map<String, Integer> MEMORY_BETA_EN_MAP = Maps.newHashMap()
	private static final Map<MediaWikiSource, Map<String, Integer>> CACHE_MAP = Maps.newHashMap()
	private static final String FORMATTED_MAP = """
${MediaWikiSource.MEMORY_ALPHA_EN.name()}:
 12 :: ${TITLE_2}
  6 :: ${TITLE_3}
${MediaWikiSource.MEMORY_BETA_EN.name()}:
 14 :: ${TITLE_5}
  8 :: ${TITLE_6}"""

	static {
		MEMORY_ALPHA_EN_MAP.put(TITLE_1, 1)
		MEMORY_ALPHA_EN_MAP.put(TITLE_2, 12)
		MEMORY_ALPHA_EN_MAP.put(TITLE_3, 6)
		MEMORY_BETA_EN_MAP.put(TITLE_4, 3)
		MEMORY_BETA_EN_MAP.put(TITLE_5, 14)
		MEMORY_BETA_EN_MAP.put(TITLE_6, 8)
		CACHE_MAP.put(MediaWikiSource.MEMORY_ALPHA_EN, MEMORY_ALPHA_EN_MAP)
		CACHE_MAP.put(MediaWikiSource.MEMORY_BETA_EN, MEMORY_BETA_EN_MAP)
	}

	FrequentHitCachingHelperDumpFormatter frequentHitCachingHelperDumpFormatter

	void setup() {
		frequentHitCachingHelperDumpFormatter = new FrequentHitCachingHelperDumpFormatter()
	}

	void "formats cache map"() {
		when:
		String formattedMap = frequentHitCachingHelperDumpFormatter.format(CACHE_MAP)

		then:
		formattedMap == FORMATTED_MAP
	}

}
