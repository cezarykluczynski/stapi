package com.cezarykluczynski.stapi.etl.character.link.relation.service

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterPageLinkWithRelationName
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApiImpl
import spock.lang.Specification

class CharacterLinkExtendedRelationsExtractorTest extends Specification {

	private static final String WIKITEXT = '[[Kurn]] (biological)<br />\n[[Nikolai Rozhenko]] (adopted)<br />\n[[Martok]]'

	private CharacterLinkExtendedRelationsExtractor characterLinkExtendedRelationsExtractor

	void setup() {
		characterLinkExtendedRelationsExtractor = new CharacterLinkExtendedRelationsExtractor(new WikitextApiImpl())
	}

	void "extracts page links with relations names form wikitext"() {
		when:
		List<CharacterPageLinkWithRelationName> characterPageLinkWithRelationNameList = characterLinkExtendedRelationsExtractor.extract(WIKITEXT)

		then:
		characterPageLinkWithRelationNameList.size() == 3
		characterPageLinkWithRelationNameList[0].pageLink.title == 'Kurn'
		characterPageLinkWithRelationNameList[0].relationName == 'biological'
		characterPageLinkWithRelationNameList[1].pageLink.title == 'Nikolai Rozhenko'
		characterPageLinkWithRelationNameList[1].relationName == 'adopted'
		characterPageLinkWithRelationNameList[2].pageLink.title == 'Martok'
		characterPageLinkWithRelationNameList[2].relationName == null
	}

}
