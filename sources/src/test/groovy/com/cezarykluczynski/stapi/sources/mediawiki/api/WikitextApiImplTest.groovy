package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import spock.lang.Specification

class WikitextApiImplTest extends Specification {

	private static final String WIKITEXT = "blah blah [[Some page|description]] and [[another page]] blah blah [[blah"

	WikitextApiImpl wikitextApiImpl

	def setup() {
		wikitextApiImpl = new WikitextApiImpl()
	}

	def "gets titles from wikitext"() {
		when:
		List<String> pageList = wikitextApiImpl
				.getPageTitlesFromWikitext(WIKITEXT)

		then:
		pageList.size() == 2
		pageList[0] == 'Some page'
		pageList[1] == 'another page'
	}

	def "gets page links from wikitext"() {
		when:
		List<PageLink> pageList = wikitextApiImpl
				.getPageLinksFromWikitext(WIKITEXT)

		then:
		pageList.size() == 2
		pageList[0].title == 'Some page'
		pageList[0].description == 'description'
		pageList[0].startPosition == 10
		pageList[0].endPosition == 35
		pageList[1].title == 'another page'
		pageList[1].description == null
		pageList[1].startPosition == 40
		pageList[1].endPosition == 56
	}

}
