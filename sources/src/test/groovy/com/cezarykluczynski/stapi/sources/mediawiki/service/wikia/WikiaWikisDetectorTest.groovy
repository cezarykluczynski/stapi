package com.cezarykluczynski.stapi.sources.mediawiki.service.wikia

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourceProperties
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties
import spock.lang.Specification

class WikiaWikisDetectorTest extends Specification {

	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN = MediaWikiSource.MEMORY_ALPHA_EN
	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_BETA_EN = MediaWikiSource.MEMORY_BETA_EN
	private static final String MEMORY_ALPHA_EN_API_URL = 'MEMORY_ALPHA_EN_API_URL'
	private static final String MEMORY_BETA_EN_API_URL = 'MEMORY_BETA_EN_API_URL'

	private MediaWikiSourcesProperties mediaWikiSourcesPropertiesMock

	private WikiaUrlDetector wikiaUrlDetectorMock

	private WikiaWikisDetector wikiaWikisDetector

	def setup() {
		mediaWikiSourcesPropertiesMock = Mock(MediaWikiSourcesProperties)
		wikiaUrlDetectorMock = Mock(WikiaUrlDetector) {
			isWikiaWikiUrl(MEMORY_ALPHA_EN_API_URL) >> true
			isWikiaWikiUrl(MEMORY_BETA_EN_API_URL) >> false
		}
		mediaWikiSourcesPropertiesMock.getMemoryAlphaEn() >> Mock(MediaWikiSourceProperties) {
			getApiUrl() >> MEMORY_ALPHA_EN_API_URL
		}
		mediaWikiSourcesPropertiesMock.getMemoryBetaEn() >> Mock(MediaWikiSourceProperties) {
			getApiUrl() >> MEMORY_BETA_EN_API_URL
		}
		wikiaWikisDetector = new WikiaWikisDetector(mediaWikiSourcesPropertiesMock, wikiaUrlDetectorMock)
	}

	def "detects Wikia wikis and not wikia Wikis"() {
		when:
		boolean memoryAlphaEnIsWikiaWiki = wikiaWikisDetector.isWikiaWiki(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then:
		memoryAlphaEnIsWikiaWiki

		when:
		boolean memoryBetaEnIsWikiaWiki = wikiaWikisDetector.isWikiaWiki(MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)

		then:
		!memoryBetaEnIsWikiaWiki
	}


}
