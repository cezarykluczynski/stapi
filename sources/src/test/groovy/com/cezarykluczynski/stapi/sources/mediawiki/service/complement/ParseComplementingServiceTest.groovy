package com.cezarykluczynski.stapi.sources.mediawiki.service.complement

import com.cezarykluczynski.stapi.sources.mediawiki.api.ParseApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.service.wikia.WikiaWikisDetector
import spock.lang.Specification

class ParseComplementingServiceTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String WIKITEXT = 'WIKITEXT'
	private static final String XML = '<root></root>'

	private WikiaWikisDetector wikiaWikisDetectorMock

	private ParseApi parseApiMock

	private ParseComplementingService parseComplementingService

	def setup() {
		wikiaWikisDetectorMock = Mock(WikiaWikisDetector)
		parseApiMock = Mock(ParseApi)
		parseComplementingService = new ParseComplementingService(wikiaWikisDetectorMock, parseApiMock)
	}

	def "does not get parsed templates when it is not Wikia's wiki"() {
		given:
		Page page = new Page(mediaWikiSource: SOURCE)

		when:
		parseComplementingService.complement(page)

		then:
		1 * wikiaWikisDetectorMock.isWikiaWiki(SOURCE) >> false
		0 * _
	}

	def "sets parsed templates when it is a Wikia's wiki"() {
		given:
		Page page = new Page(
				mediaWikiSource: SOURCE,
				wikitext: WIKITEXT)

		when:
		parseComplementingService.complement(page)

		then:
		1 * wikiaWikisDetectorMock.isWikiaWiki(SOURCE) >> true
		1 * parseApiMock.parseWikitextToXmlTree(WIKITEXT) >> XML
		0 * _
	}

}
