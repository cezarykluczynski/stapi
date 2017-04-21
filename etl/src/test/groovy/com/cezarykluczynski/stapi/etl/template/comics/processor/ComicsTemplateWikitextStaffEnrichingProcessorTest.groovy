package com.cezarykluczynski.stapi.etl.template.comics.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.WikitextList
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.common.service.WikitextListsExtractor
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicsTemplateWikitextStaffEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String CHILD_WIKITEXT = 'CHILD_WIKITEXT'
	private static final String CHILD_PAGE_TITLE = 'CHILD_PAGE_TITLE'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private WikitextListsExtractor wikitextListsExtractorMock

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private ComicsTemplateWikitextStaffEnrichingProcessor comicsTemplateWikitextStaffEnrichingProcessor

	void setup() {
		wikitextListsExtractorMock = Mock()
		wikitextApiMock = Mock()
		entityLookupByNameServiceMock = Mock()
		comicsTemplateWikitextStaffEnrichingProcessor = new ComicsTemplateWikitextStaffEnrichingProcessor(wikitextListsExtractorMock,
				wikitextApiMock, entityLookupByNameServiceMock)
	}

	void "extracts writers from wikitext"() {
		given:
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		WikitextList wikitextList = new WikitextList(
				text: ComicsTemplateWikitextStaffEnrichingProcessor.WRITERS_TITLES[0],
				children: Lists.newArrayList(
						new WikitextList(text: CHILD_WIKITEXT)
				))
		Staff writer = Mock()

		when:
		comicsTemplateWikitextStaffEnrichingProcessor.enrich(EnrichablePair.of(WIKITEXT, comicsTemplate))

		then:
		1 * wikitextListsExtractorMock.extractListsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList)
		1 * wikitextApiMock.getPageTitlesFromWikitext(ComicsTemplateWikitextStaffEnrichingProcessor.WRITERS_TITLES[0]) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageTitlesFromWikitext(CHILD_WIKITEXT) >> Lists.newArrayList(CHILD_PAGE_TITLE)
		1 * entityLookupByNameServiceMock.findStaffByName(CHILD_PAGE_TITLE, MEDIA_WIKI_SOURCE) >> Optional.of(writer)
		0 * _
		comicsTemplate.writers.size() == 1
		comicsTemplate.writers.contains writer
		comicsTemplate.artists.empty
		comicsTemplate.editors.empty
		comicsTemplate.staff.empty
	}

	void "extracts artists from wikitext"() {
		given:
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		WikitextList wikitextList = new WikitextList(
				text: ComicsTemplateWikitextStaffEnrichingProcessor.ARTISTS_PREFIXES[0],
				children: Lists.newArrayList(
						new WikitextList(text: CHILD_WIKITEXT)
				))
		Staff artist = Mock()

		when:
		comicsTemplateWikitextStaffEnrichingProcessor.enrich(EnrichablePair.of(WIKITEXT, comicsTemplate))

		then:
		1 * wikitextListsExtractorMock.extractListsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList)
		1 * wikitextApiMock.getPageTitlesFromWikitext(ComicsTemplateWikitextStaffEnrichingProcessor.ARTISTS_PREFIXES[0]) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageTitlesFromWikitext(CHILD_WIKITEXT) >> Lists.newArrayList(CHILD_PAGE_TITLE)
		1 * entityLookupByNameServiceMock.findStaffByName(CHILD_PAGE_TITLE, MEDIA_WIKI_SOURCE) >> Optional.of(artist)
		0 * _
		comicsTemplate.artists.size() == 1
		comicsTemplate.artists.contains artist
		comicsTemplate.writers.empty
		comicsTemplate.editors.empty
		comicsTemplate.staff.empty
	}

	void "extracts editors from wikitext"() {
		given:
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		WikitextList wikitextList = new WikitextList(
				text: ComicsTemplateWikitextStaffEnrichingProcessor.EDITORS_PREFIXES[0],
				children: Lists.newArrayList(
						new WikitextList(text: CHILD_WIKITEXT)
				))
		Staff editor = Mock()

		when:
		comicsTemplateWikitextStaffEnrichingProcessor.enrich(EnrichablePair.of(WIKITEXT, comicsTemplate))

		then:
		1 * wikitextListsExtractorMock.extractListsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList)
		1 * wikitextApiMock.getPageTitlesFromWikitext(ComicsTemplateWikitextStaffEnrichingProcessor.EDITORS_PREFIXES[0]) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageTitlesFromWikitext(CHILD_WIKITEXT) >> Lists.newArrayList(CHILD_PAGE_TITLE)
		1 * entityLookupByNameServiceMock.findStaffByName(CHILD_PAGE_TITLE, MEDIA_WIKI_SOURCE) >> Optional.of(editor)
		0 * _
		comicsTemplate.editors.size() == 1
		comicsTemplate.editors.contains editor
		comicsTemplate.artists.empty
		comicsTemplate.writers.empty
		comicsTemplate.staff.empty
	}

	void "extracts staff from wikitext"() {
		given:
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		WikitextList wikitextList = new WikitextList(
				text: ComicsTemplateWikitextStaffEnrichingProcessor.STAFF_TITLES[0],
				children: Lists.newArrayList(
						new WikitextList(text: CHILD_WIKITEXT)
				))
		Staff staff = Mock()

		when:
		comicsTemplateWikitextStaffEnrichingProcessor.enrich(EnrichablePair.of(WIKITEXT, comicsTemplate))

		then:
		1 * wikitextListsExtractorMock.extractListsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList)
		1 * wikitextApiMock.getPageTitlesFromWikitext(ComicsTemplateWikitextStaffEnrichingProcessor.STAFF_TITLES[0]) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageTitlesFromWikitext(CHILD_WIKITEXT) >> Lists.newArrayList(CHILD_PAGE_TITLE)
		1 * entityLookupByNameServiceMock.findStaffByName(CHILD_PAGE_TITLE, MEDIA_WIKI_SOURCE) >> Optional.of(staff)
		0 * _
		comicsTemplate.staff.size() == 1
		comicsTemplate.staff.contains staff
		comicsTemplate.artists.empty
		comicsTemplate.writers.empty
		comicsTemplate.editors.empty
	}

	void "ignores ignorable section"() {
		given:
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		WikitextList wikitextList = new WikitextList(text: ComicsTemplateWikitextStaffEnrichingProcessor.DEDICATED_TO)

		when:
		comicsTemplateWikitextStaffEnrichingProcessor.enrich(EnrichablePair.of(WIKITEXT, comicsTemplate))

		then:
		1 * wikitextListsExtractorMock.extractListsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList)
		0 * _
		comicsTemplate.writers.empty
		comicsTemplate.artists.empty
		comicsTemplate.editors.empty
		comicsTemplate.staff.empty
	}

	void "logs title of unrecognized section"() {
		given:
		ComicsTemplate comicsTemplate = Mock()
		WikitextList wikitextList = new WikitextList(text: 'Unrecognized')

		when:
		comicsTemplateWikitextStaffEnrichingProcessor.enrich(EnrichablePair.of(WIKITEXT, comicsTemplate))

		then:
		1 * wikitextListsExtractorMock.extractListsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList)
		1 * comicsTemplate.title
		0 * _
	}

}
