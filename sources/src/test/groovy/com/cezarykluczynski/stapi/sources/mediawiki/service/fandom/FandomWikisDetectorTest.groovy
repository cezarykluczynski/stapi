package com.cezarykluczynski.stapi.sources.mediawiki.service.fandom

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourceProperties
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties
import spock.lang.Specification

class FandomWikisDetectorTest extends Specification {

	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN = MediaWikiSource.MEMORY_ALPHA_EN
	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_BETA_EN = MediaWikiSource.MEMORY_BETA_EN
	private static final String MEMORY_ALPHA_EN_API_URL = 'MEMORY_ALPHA_EN_API_URL'
	private static final String MEMORY_BETA_EN_API_URL = 'MEMORY_BETA_EN_API_URL'

	private MediaWikiSourcesProperties mediaWikiSourcesPropertiesMock

	private FandomUrlDetector fandomUrlDetectorMock

	private FandomWikisDetector fandomWikisDetector

	void setup() {
		mediaWikiSourcesPropertiesMock = Mock()
		fandomUrlDetectorMock = Mock()
		fandomUrlDetectorMock.isFandomWikiUrl(MEMORY_ALPHA_EN_API_URL) >> true
		fandomUrlDetectorMock.isFandomWikiUrl(MEMORY_BETA_EN_API_URL) >> false
		MediaWikiSourceProperties memoryAlphaEnMediaWikiSourceProperties = Mock()
		memoryAlphaEnMediaWikiSourceProperties.apiUrl >> MEMORY_ALPHA_EN_API_URL
		mediaWikiSourcesPropertiesMock.memoryAlphaEn >> memoryAlphaEnMediaWikiSourceProperties
		MediaWikiSourceProperties memoryBetaEnMediaWikiSourceProperties = Mock()
		memoryBetaEnMediaWikiSourceProperties.apiUrl >> MEMORY_BETA_EN_API_URL
		mediaWikiSourcesPropertiesMock.memoryBetaEn >> memoryBetaEnMediaWikiSourceProperties
		fandomWikisDetector = new FandomWikisDetector(mediaWikiSourcesPropertiesMock, fandomUrlDetectorMock)
	}

	void "detects Fandom wikis and not Fandom wikis"() {
		when:
		boolean memoryAlphaEnIsFandomWiki = fandomWikisDetector.isFandomWiki(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then:
		memoryAlphaEnIsFandomWiki

		when:
		boolean memoryBetaEnIsFandomWiki = fandomWikisDetector.isFandomWiki(MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)

		then:
		!memoryBetaEnIsFandomWiki
	}

}
