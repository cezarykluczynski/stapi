package com.cezarykluczynski.stapi.etl.template.starship.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import spock.lang.Specification

class StarshipRegistryProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String WIKITEXT_WITHOUT_LINKS = 'WIKITEXT_WITHOUT_LINKS'
	private static final String WIKITEXT_WITHOUT_LINKS_MULTILINE = 'WIKITEXT_WITHOUT_LINKS<br />MULTILINE'

	private WikitextApi wikitextApiMock

	private StarshipRegistryProcessor starshipRegistryProcessor

	void setup() {
		wikitextApiMock = Mock()
		starshipRegistryProcessor = new StarshipRegistryProcessor(wikitextApiMock)
	}

	void "when null is passed, null is returned"() {
		when:
		String registry = starshipRegistryProcessor.process(null)

		then:
		0 * _
		registry == null
	}

	void "when empty string is passed, null is returned"() {
		when:
		String registry = starshipRegistryProcessor.process('')

		then:
		0 * _
		registry == null
	}

	void "when valid input is passed, result of links removal is returned"() {
		when:
		String registry = starshipRegistryProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getWikitextWithoutLinks(WIKITEXT) >> WIKITEXT_WITHOUT_LINKS
		0 * _
		registry == WIKITEXT_WITHOUT_LINKS
	}

	void "when valid input is passed, and multiline wikitext without links is returned by WikitextApi, first line is returned"() {
		when:
		String registry = starshipRegistryProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getWikitextWithoutLinks(WIKITEXT) >> WIKITEXT_WITHOUT_LINKS_MULTILINE
		0 * _
		registry == WIKITEXT_WITHOUT_LINKS
	}

}
