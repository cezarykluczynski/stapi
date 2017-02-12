package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.etl.common.dto.WikitextList
import spock.lang.Specification

class WikitextListsExtractorTest extends Specification {

	private static final String LIST_WIKITEXT = '''
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

	private static final String DEFINITION_WIKITEXT = '''
:Section to skip
;[[James T. Kirk]]
: {{USS|Enterprise|NCC-1701}}'s [[CO]], a [[Starfleet]] [[captain]].
;[[Spock]]
: Half-[[Vulcan]] ''Enterprise'' [[XO]] and [[science officer]], a Starfleet commander.
;[[Leonard McCoy]]
: ''Enterprise'' [[CMO]], a Starfleet [[doctor]] with the rank of [[commander]].
;[[Montgomery Scott]]
: ''Enterprise'' [[chief engineer]], a Starfleet commander.
'''

	private WikitextListsExtractor wikitextListsExtractor

	void setup() {
		wikitextListsExtractor = new WikitextListsExtractor()
	}

	void "parses wikitext containing list to WikitextList tree"() {
		when:
		List<WikitextList> wikitextListList = wikitextListsExtractor.extractListsFromWikitext(LIST_WIKITEXT)

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

	void "parses wikitext containing definitions to WikitextList tree"() {
		when:
		List<WikitextList> wikitextListList = wikitextListsExtractor.extractDefinitionsFromWikitext(DEFINITION_WIKITEXT)

		then:
		wikitextListList.size() == 4
		wikitextListList[0].text == '[[James T. Kirk]]'
		wikitextListList[0].level == 1
		wikitextListList[0].children.size() == 1
		wikitextListList[0].children[0].text == '{{USS|Enterprise|NCC-1701}}\'s [[CO]], a [[Starfleet]] [[captain]].'
		wikitextListList[0].children[0].level == 2
		wikitextListList[0].children[0].children.empty
		wikitextListList[1].text == '[[Spock]]'
		wikitextListList[1].level == 1
		wikitextListList[1].children.size() == 1
		wikitextListList[1].children[0].text == 'Half-[[Vulcan]] \'\'Enterprise\'\' [[XO]] and [[science officer]], a Starfleet commander.'
		wikitextListList[1].children[0].level == 2
		wikitextListList[1].children[0].children.empty
		wikitextListList[2].text == '[[Leonard McCoy]]'
		wikitextListList[2].level == 1
		wikitextListList[2].children.size() == 1
		wikitextListList[2].children[0].text == '\'\'Enterprise\'\' [[CMO]], a Starfleet [[doctor]] with the rank of [[commander]].'
		wikitextListList[2].children[0].level == 2
		wikitextListList[2].children[0].children.empty
		wikitextListList[3].text == '[[Montgomery Scott]]'
		wikitextListList[3].level == 1
		wikitextListList[3].children.size() == 1
		wikitextListList[3].children[0].text == '\'\'Enterprise\'\' [[chief engineer]], a Starfleet commander.'
		wikitextListList[3].children[0].level == 2
		wikitextListList[3].children[0].children.empty
	}

	void "returns empty list for null input"() {
		when:
		List<WikitextList> wikitextListList = wikitextListsExtractor.extractListsFromWikitext(null)

		then:
		wikitextListList.empty
	}

}
