package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.etl.common.dto.WikitextList
import spock.lang.Specification

class WikitextListsExtractorTest extends Specification {

	private static final String WIKITEXT = '''
* Writers:
** [[Mike Johnson]]
** [[F. Leonard Johnson]], MD
* Artists:
** [[Claudia Balboni]] (interior art)
** [[Erica Durante]] (inking art)
<!--
** [[Grant Goleash]] (cover color art)
-->
* Editor:
** [[Scott Dunbier]]
* Creative consultant: [[Roberto Orci]]
* Special thanks to [[Risa Kessler]] and [[John Van Citters]] at CBS Consumer Products.
'''

	private WikitextListsExtractor wikitextListsExtractor

	void setup() {
		wikitextListsExtractor = new WikitextListsExtractor()
	}

	void "parses wikitext to WikitextList tree"() {
		when:
		List<WikitextList> wikitextListList = wikitextListsExtractor.extractFromWikitext(WIKITEXT)

		then:
		wikitextListList.size() == 5
		wikitextListList[0].text == 'Writers:'
		wikitextListList[0].level == 1
		wikitextListList[0].children.size() == 2
		wikitextListList[0].children[0].text == '[[Mike Johnson]]'
		wikitextListList[0].children[0].level == 2
		wikitextListList[0].children[0].children.empty
		wikitextListList[0].children[1].text == '[[F. Leonard Johnson]], MD'
		wikitextListList[0].children[1].level == 2
		wikitextListList[0].children[1].children.empty
		wikitextListList[1].text == 'Artists:'
		wikitextListList[1].level == 1
		wikitextListList[1].children.size() == 2
		wikitextListList[1].children[0].text == '[[Claudia Balboni]] (interior art)'
		wikitextListList[1].children[0].level == 2
		wikitextListList[1].children[0].children.empty
		wikitextListList[1].children[1].text == '[[Erica Durante]] (inking art)'
		wikitextListList[1].children[1].level == 2
		wikitextListList[1].children[1].children.empty
		wikitextListList[3].text == 'Creative consultant: [[Roberto Orci]]'
		wikitextListList[3].level == 1
		wikitextListList[3].children.empty
		wikitextListList[4].text == 'Special thanks to [[Risa Kessler]] and [[John Van Citters]] at CBS Consumer Products.'
		wikitextListList[4].level == 1
		wikitextListList[4].children.empty
	}

	void "returns empty list for null input"() {
		when:
		List<WikitextList> wikitextListList = wikitextListsExtractor.extractFromWikitext(null)

		then:
		wikitextListList.empty
	}

}
