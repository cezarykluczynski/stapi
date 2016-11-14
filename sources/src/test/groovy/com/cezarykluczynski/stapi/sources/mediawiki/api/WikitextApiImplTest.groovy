package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import spock.lang.Specification

class WikitextApiImplTest extends Specification {

	WikitextApiImpl wikitextApiImpl

	def setup() {
		wikitextApiImpl = new WikitextApiImpl()
	}

	def "gets titles from wikitext"() {
		when:
		List<String> pageList = wikitextApiImpl
				.getPageTitlesFromWikitext("blah blah [[Some page|some page]] and [[another page]] blah blah [[blah")

		then:
		pageList.size() == 2
		pageList[0] == 'Some page'
		pageList[1] == 'another page'
	}

	def "gets page links from wikitext"() {
		when:
		List<PageLink> pageList = wikitextApiImpl
				.getPageLinksFromWikitext("blah blah [[Some page|description]] and [[another page]] blah blah [[blah")

		then:
		pageList.size() == 2
		pageList[0].title == 'Some page'
		pageList[0].description == 'description'
		pageList[1].title == 'another page'
		pageList[1].description == null
	}

}
