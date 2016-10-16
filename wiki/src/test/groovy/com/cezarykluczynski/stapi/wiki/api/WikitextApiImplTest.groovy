package com.cezarykluczynski.stapi.wiki.api

import spock.lang.Specification

class WikitextApiImplTest extends Specification {

	WikitextApiImpl wikitextApiImpl

	def setup() {
		wikitextApiImpl = new WikitextApiImpl()
	}

	def "gets titles from wikitext"() {
		when:
		List<String> pageList = wikitextApiImpl
				.getPageTitlesFromWikitext("blah blah [[Some page|some page]] and [[another page]] blah blah")

		then:
		pageList.size() == 2
		pageList[0] == 'Some page'
		pageList[1] == 'another page'
	}

}
