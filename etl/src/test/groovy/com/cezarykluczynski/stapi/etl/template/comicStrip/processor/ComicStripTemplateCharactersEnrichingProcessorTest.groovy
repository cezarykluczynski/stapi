package com.cezarykluczynski.stapi.etl.template.comicStrip.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.etl.template.comicStrip.dto.ComicStripTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicStripTemplateCharactersEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String PAGE_TITLE_1 = 'PAGE_TITLE_1'
	private static final String PAGE_TITLE_2 = 'PAGE_TITLE_2'
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageSectionExtractor pageSectionExtractorMock

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private ComicStripTemplateCharactersEnrichingProcessor comicStripTemplateCharactersEnrichingProcessor

	void setup() {
		pageSectionExtractorMock = Mock(PageSectionExtractor)
		wikitextApiMock = Mock(WikitextApi)
		entityLookupByNameServiceMock = Mock(EntityLookupByNameService)
		comicStripTemplateCharactersEnrichingProcessor = new ComicStripTemplateCharactersEnrichingProcessor(pageSectionExtractorMock,
				wikitextApiMock, entityLookupByNameServiceMock)
	}

	void "adds found characters to ComicStripTemplate"() {
		given:
		Character character = Mock(Character)
		Page page = new Page()
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()
		PageSection pageSection = new PageSection(wikitext: WIKITEXT)

		when:
		comicStripTemplateCharactersEnrichingProcessor.enrich(EnrichablePair.of(page, comicStripTemplate))

		then:
		1 * pageSectionExtractorMock.findByTitlesIncludingSubsections(page, ComicStripTemplateCharactersEnrichingProcessor.CHARACTERS,
				ComicStripTemplateCharactersEnrichingProcessor.REGULAR_CAST) >> Lists.newArrayList(pageSection)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1, PAGE_TITLE_2)
		1 * entityLookupByNameServiceMock.findCharacterByName(PAGE_TITLE_1, SOURCE) >> Optional.of(character)
		1 * entityLookupByNameServiceMock.findCharacterByName(PAGE_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		comicStripTemplate.characters.size() == 1
		comicStripTemplate.characters.contains character
	}

}
