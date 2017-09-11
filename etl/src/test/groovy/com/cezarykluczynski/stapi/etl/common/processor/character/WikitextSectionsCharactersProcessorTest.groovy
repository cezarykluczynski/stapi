package com.cezarykluczynski.stapi.etl.common.processor.character

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextSectionsCharactersProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String PAGE_TITLE_1 = 'PAGE_TITLE_1'
	private static final String PAGE_TITLE_2 = 'PAGE_TITLE_2'
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageSectionExtractor pageSectionExtractorMock

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private WikitextSectionsCharactersProcessor wikitextSectionsCharactersProcessor

	void setup() {
		pageSectionExtractorMock = Mock()
		wikitextApiMock = Mock()
		entityLookupByNameServiceMock = Mock()
		wikitextSectionsCharactersProcessor = new WikitextSectionsCharactersProcessor(pageSectionExtractorMock, wikitextApiMock,
				entityLookupByNameServiceMock)
	}

	void "adds found characters to ComicStripTemplate"() {
		given:
		Character character = Mock()
		Page page = new Page()
		PageSection pageSection = new PageSection(wikitext: WIKITEXT)

		when:
		Set<Character> characterSet = wikitextSectionsCharactersProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitlesIncludingSubsections(page, WikitextSectionsCharactersProcessor.CHARACTERS,
				WikitextSectionsCharactersProcessor.REGULAR_CAST) >> Lists.newArrayList(pageSection)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1, PAGE_TITLE_2)
		1 * entityLookupByNameServiceMock.findCharacterByName(PAGE_TITLE_1, SOURCE) >> Optional.of(character)
		1 * entityLookupByNameServiceMock.findCharacterByName(PAGE_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		characterSet.size() == 1
		characterSet.contains character
	}

}
