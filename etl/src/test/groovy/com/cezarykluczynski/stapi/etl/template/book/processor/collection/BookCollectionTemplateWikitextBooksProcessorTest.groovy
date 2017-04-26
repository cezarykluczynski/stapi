package com.cezarykluczynski.stapi.etl.template.book.processor.collection

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class BookCollectionTemplateWikitextBooksProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String WIKITEXT_1 = 'WIKITEXT_1'
	private static final String WIKITEXT_2 = 'WIKITEXT_2'
	private static final String PAGE_TITLE_1 = 'PAGE_TITLE_1'
	private static final String PAGE_TITLE_2 = 'PAGE_TITLE_2'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageSectionExtractor pageSectionExtractorMock

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private BookCollectionTemplateWikitextBooksProcessor bookCollectionTemplateWikitextBooksProcessor

	void setup() {
		pageSectionExtractorMock = Mock()
		wikitextApiMock = Mock()
		entityLookupByNameServiceMock = Mock()
		bookCollectionTemplateWikitextBooksProcessor = new BookCollectionTemplateWikitextBooksProcessor(pageSectionExtractorMock, wikitextApiMock,
				entityLookupByNameServiceMock)
	}

	void "when no pages sections are found, page wikitext is used to parse"() {
		given:
		Page page = new Page(wikitext: WIKITEXT)

		when:
		Set<Book> bookSet = bookCollectionTemplateWikitextBooksProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitles(*_) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList()
		0 * _
		bookSet.empty
	}

	void "filters out 'Summary' section when there are other sections" () {
		given:
		Page page = new Page()
		PageSection pageSection = new PageSection(wikitext: WIKITEXT_1)
		PageSection pageSectionSummary = new PageSection(wikitext: WIKITEXT_2, text: BookCollectionTemplateWikitextBooksProcessor.SUMMARY)
		Book book = Mock()

		when:
		Set<Book> bookSet = bookCollectionTemplateWikitextBooksProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitles(*_) >> Lists.newArrayList(pageSectionSummary, pageSection)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_1) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * entityLookupByNameServiceMock.findBookByName(PAGE_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.of(book)
		0 * _
		bookSet.size() == 1
		bookSet.contains book
	}

	void "when two sections are found, first one is used"() {
		given:
		Page page = new Page()
		PageSection pageSection1 = new PageSection(wikitext: WIKITEXT_1)
		PageSection pageSection2 = new PageSection(wikitext: WIKITEXT_2)
		Book book = Mock()

		when:
		Set<Book> bookSet = bookCollectionTemplateWikitextBooksProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitles(*_) >> Lists.newArrayList(pageSection1, pageSection2)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_1) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * entityLookupByNameServiceMock.findBookByName(PAGE_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.of(book)
		0 * _
		bookSet.size() == 1
		bookSet.contains book
	}

	void "when no books are found in sections, page wikitext is used to parse" () {
		given:
		Page page = new Page(wikitext: WIKITEXT)
		PageSection pageSection = new PageSection(wikitext: WIKITEXT_1)
		Book book = Mock()

		when:
		Set<Book> bookSet = bookCollectionTemplateWikitextBooksProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitles(*_) >> Lists.newArrayList(pageSection)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_1) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * entityLookupByNameServiceMock.findBookByName(PAGE_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_2)
		1 * entityLookupByNameServiceMock.findBookByName(PAGE_TITLE_2, MEDIA_WIKI_SOURCE) >> Optional.of(book)
		0 * _
		bookSet.size() == 1
		bookSet.contains book
	}

	void "when no books can be found, empty set is returned"() {
		given:
		Page page = new Page(wikitext: WIKITEXT)
		PageSection pageSection1 = new PageSection(wikitext: WIKITEXT_1)

		when:
		Set<Book> bookSet = bookCollectionTemplateWikitextBooksProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitles(*_) >> Lists.newArrayList(pageSection1)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_1) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * entityLookupByNameServiceMock.findBookByName(PAGE_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList()
		0 * _
		bookSet.empty
	}

}
