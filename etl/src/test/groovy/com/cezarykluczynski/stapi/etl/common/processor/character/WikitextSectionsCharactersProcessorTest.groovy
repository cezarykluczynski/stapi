package com.cezarykluczynski.stapi.etl.common.processor.character

import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextSectionsCharactersProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private PageSectionExtractor pageSectionExtractorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private WikitextSectionsCharactersProcessor wikitextSectionsCharactersProcessor

	void setup() {
		pageSectionExtractorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		wikitextSectionsCharactersProcessor = new WikitextSectionsCharactersProcessor(pageSectionExtractorMock, wikitextToEntitiesProcessorMock)
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
		1 * wikitextToEntitiesProcessorMock.findCharacters(WIKITEXT) >> Lists.newArrayList(character)
		0 * _
		characterSet.size() == 1
		characterSet.contains character
	}

}
