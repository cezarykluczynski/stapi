package com.cezarykluczynski.stapi.etl.template.comic_strip.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplate
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplateParameter
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicStripTemplatePartStaffEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String PAGE_LINK_TITLE_1 = 'PAGE_LINK_TITLE_1'
	private static final String PAGE_LINK_TITLE_2 = 'PAGE_LINK_TITLE_2'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private ComicStripTemplatePartStaffEnrichingProcessor comicStripTemplatePartStaffEnrichingProcessor

	void setup() {
		wikitextApiMock = Mock()
		entityLookupByNameServiceMock = Mock()
		comicStripTemplatePartStaffEnrichingProcessor = new ComicStripTemplatePartStaffEnrichingProcessor(wikitextApiMock, entityLookupByNameServiceMock)
	}

	void "gets writers from wikitext"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicStripTemplateParameter.WRITER, value: WIKITEXT)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()
		Staff staff1 = Mock()
		Staff staff2 = Mock()

		when:
		comicStripTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicStripTemplate))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_LINK_TITLE_1, PAGE_LINK_TITLE_2)
		1 * entityLookupByNameServiceMock.findStaffByName(PAGE_LINK_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.of(staff1)
		1 * entityLookupByNameServiceMock.findStaffByName(PAGE_LINK_TITLE_2, MEDIA_WIKI_SOURCE) >> Optional.of(staff2)
		0 * _
		comicStripTemplate.writers.size() == 2
		comicStripTemplate.writers.contains staff1
		comicStripTemplate.writers.contains staff2
		comicStripTemplate.artists.empty
	}

	void "gets artists from wikitext"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicStripTemplateParameter.ARTIST, value: WIKITEXT)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()
		Staff staff1 = Mock()
		Staff staff2 = Mock()

		when:
		comicStripTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicStripTemplate))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_LINK_TITLE_1, PAGE_LINK_TITLE_2)
		1 * entityLookupByNameServiceMock.findStaffByName(PAGE_LINK_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.of(staff1)
		1 * entityLookupByNameServiceMock.findStaffByName(PAGE_LINK_TITLE_2, MEDIA_WIKI_SOURCE) >> Optional.of(staff2)
		0 * _
		comicStripTemplate.artists.size() == 2
		comicStripTemplate.artists.contains staff1
		comicStripTemplate.artists.contains staff2
		comicStripTemplate.writers.empty
	}

	void "tolerates parts with unknown key"() {
		given:
		Template.Part templatePart = new Template.Part(key: '', value: WIKITEXT)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()
		Staff staff1 = Mock()

		when:
		comicStripTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicStripTemplate))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_LINK_TITLE_1)
		1 * entityLookupByNameServiceMock.findStaffByName(PAGE_LINK_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.of(staff1)
		0 * _
		comicStripTemplate.writers.empty
		comicStripTemplate.artists.empty
		notThrown(Exception)
	}

}
