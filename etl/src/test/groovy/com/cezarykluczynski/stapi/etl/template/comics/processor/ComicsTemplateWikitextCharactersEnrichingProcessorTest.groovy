package com.cezarykluczynski.stapi.etl.template.comics.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.WikitextList
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.common.service.WikitextListsExtractor
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class ComicsTemplateWikitextCharactersEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String WIKITEXT_LIST_1 = 'WIKITEXT_LIST_1'
	private static final String WIKITEXT_LIST_2 = 'WIKITEXT_LIST_2'
	private static final String CHARACTER_LINK_TITLE_1 = 'CHARACTER_LINK_TITLE_1'
	private static final String CHARACTER_LINK_TITLE_2 = 'CHARACTER_LINK_TITLE_2'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private WikitextListsExtractor wikitextListsExtractorMock

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private ComicsTemplateWikitextCharactersEnrichingProcessor comicsTemplateWikitextCharactersEnrichingProcessor

	void setup() {
		wikitextListsExtractorMock = Mock(WikitextListsExtractor)
		wikitextApiMock = Mock(WikitextApi)
		entityLookupByNameServiceMock = Mock(EntityLookupByNameService)
		comicsTemplateWikitextCharactersEnrichingProcessor = new ComicsTemplateWikitextCharactersEnrichingProcessor(wikitextListsExtractorMock,
				wikitextApiMock, entityLookupByNameServiceMock)
	}

	void "adds found characters to ComicsTemplate"() {
		given:
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		WikitextList wikitextList1 = new WikitextList(text: WIKITEXT_LIST_1)
		WikitextList wikitextList2 = new WikitextList(text: WIKITEXT_LIST_2)
		Character character = Mock(Character)

		when:
		comicsTemplateWikitextCharactersEnrichingProcessor.enrich(EnrichablePair.of(WIKITEXT, comicsTemplate))

		then:
		1 * wikitextListsExtractorMock.extractDefinitionsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList1, wikitextList2)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_LIST_1) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_LIST_2) >> Lists.newArrayList(CHARACTER_LINK_TITLE_1, CHARACTER_LINK_TITLE_2)
		1 * entityLookupByNameServiceMock.findCharacterByName(CHARACTER_LINK_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.of(character)
		0 * _
		comicsTemplate.characters.size() == 1
		comicsTemplate.characters.contains character
	}

	void "logs title when no characters were found, but at least one WikitextList was found"() {
		given:
		ComicsTemplate comicsTemplate = Mock(ComicsTemplate)
		WikitextList wikitextList1 = new WikitextList(text: WIKITEXT_LIST_1)

		when:
		comicsTemplateWikitextCharactersEnrichingProcessor.enrich(EnrichablePair.of(WIKITEXT, comicsTemplate))

		then:
		1 * wikitextListsExtractorMock.extractDefinitionsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList1)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_LIST_1) >> Lists.newArrayList()
		1 * comicsTemplate.characters >> Sets.newHashSet()
		1 * comicsTemplate.title
		0 * _
	}

}
