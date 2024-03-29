package com.cezarykluczynski.stapi.etl.mediawiki.service.complement

import com.cezarykluczynski.stapi.etl.mediawiki.api.ParseApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.etl.mediawiki.parser.JsonTemplateParser
import com.cezarykluczynski.stapi.etl.mediawiki.service.fandom.FandomWikisDetector
import spock.lang.Specification

class ParseComplementingServiceTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String WIKITEXT = 'WIKITEXT'
	private static final String XML = '<root></root>'

	private FandomWikisDetector fandomWikisDetectorMock

	private ParseApi parseApiMock

	private JsonTemplateParser jsonTemplateParserMock

	private ParseComplementingService parseComplementingService

	void setup() {
		fandomWikisDetectorMock = Mock()
		parseApiMock = Mock()
		jsonTemplateParserMock = Mock()
		parseComplementingService = new ParseComplementingService(fandomWikisDetectorMock, parseApiMock, jsonTemplateParserMock)
	}

	void "does not get parsed templates when it is not Fandom's wiki"() {
		given:
		Page page = new Page(mediaWikiSource: SOURCE)

		when:
		parseComplementingService.complement(page)

		then:
		1 * fandomWikisDetectorMock.isFandomWiki(SOURCE) >> false
		0 * _
	}

	void "sets parsed templates when it is a Fandom's wiki, and wikitext could be parsed"() {
		given:
		Page page = new Page(
				mediaWikiSource: SOURCE,
				wikitext: WIKITEXT)
		Template template = Mock()

		when:
		parseComplementingService.complement(page)

		then:
		1 * fandomWikisDetectorMock.isFandomWiki(SOURCE) >> true
		1 * parseApiMock.parseWikitextToXmlTree(WIKITEXT) >> XML
		1 * jsonTemplateParserMock.parse(XML) >> [template]
		0 * _
		page.templates == [template]
	}

	void "does not set parsed templates when it is a Fandom's wiki, but wikitext could not be parsed"() {
		given:
		Page page = new Page(
				mediaWikiSource: SOURCE,
				wikitext: WIKITEXT)

		when:
		parseComplementingService.complement(page)

		then:
		1 * fandomWikisDetectorMock.isFandomWiki(SOURCE) >> true
		1 * parseApiMock.parseWikitextToXmlTree(WIKITEXT) >> null
		0 * _
		page.templates == []
	}

}
