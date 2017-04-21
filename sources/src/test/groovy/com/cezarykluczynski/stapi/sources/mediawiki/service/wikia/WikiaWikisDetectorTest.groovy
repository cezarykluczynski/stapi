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

	void setup() {
		mediaWikiSourcesPropertiesMock = Mock()
		wikiaUrlDetectorMock = Mock()
		wikiaUrlDetectorMock.isWikiaWikiUrl(MEMORY_ALPHA_EN_API_URL) >> true
		wikiaUrlDetectorMock.isWikiaWikiUrl(MEMORY_BETA_EN_API_URL) >> false
		MediaWikiSourceProperties memoryAlphaEnMediaWikiSourceProperties = Mock()
		memoryAlphaEnMediaWikiSourceProperties.apiUrl >> MEMORY_ALPHA_EN_API_URL
		mediaWikiSourcesPropertiesMock.memoryAlphaEn >> memoryAlphaEnMediaWikiSourceProperties
		MediaWikiSourceProperties memoryBetaEnMediaWikiSourceProperties = Mock()
		memoryBetaEnMediaWikiSourceProperties.apiUrl >> MEMORY_BETA_EN_API_URL
		mediaWikiSourcesPropertiesMock.memoryBetaEn >> memoryBetaEnMediaWikiSourceProperties
		wikiaWikisDetector = new WikiaWikisDetector(mediaWikiSourcesPropertiesMock, wikiaUrlDetectorMock)
	}

	void "detects Wikia wikis and not wikia Wikis"() {
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
