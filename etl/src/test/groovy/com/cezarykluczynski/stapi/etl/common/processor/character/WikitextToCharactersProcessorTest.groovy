package com.cezarykluczynski.stapi.etl.common.processor.character

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToCharactersProcessorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String LINK_TITLE_1 = 'LINK_TITLE_1'
	private static final String LINK_TITLE_2 = 'LINK_TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private CharacterRepository characterRepositoryMock

	private WikitextToCharactersProcessor wikitextToCharactersProcessor

	void setup() {
		wikitextApiMock = Mock()
		characterRepositoryMock = Mock()
		wikitextToCharactersProcessor = new WikitextToCharactersProcessor(wikitextApiMock, characterRepositoryMock)
	}

	void "given wikitext, gets links from it, then returns companies that have associated pages with matching titles"() {
		given:
		Character character = Mock()

		when:
		Set<Character> characterSet = wikitextToCharactersProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.of(character)
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		characterSet.size() == 1
		characterSet.contains character
	}

}
