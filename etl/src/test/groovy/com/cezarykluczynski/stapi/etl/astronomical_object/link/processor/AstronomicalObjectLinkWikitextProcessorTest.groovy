package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor

import com.cezarykluczynski.stapi.etl.common.service.ParagraphExtractor
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink
import com.google.common.collect.Lists
import jakarta.persistence.NonUniqueResultException
import spock.lang.Specification

class AstronomicalObjectLinkWikitextProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String LIGTH_YEAR_WIKITEXT = 'light year WIKITEXT'
	private static final String VISILBE_FROM_WIKITEXT = 'visible from WIKITEXT'
	private static final String WITH_LINE_BREAK_WIKITEXT = 'light years <br/> WIKITEXT'
	private static final String WIKITEXT_WITHOUT_TEMPLATES = 'WIKITEXT_WITHOUT_TEMPLATES'
	private static final String PAGE_LINK_TITLE = 'WIKITEXT_WITHOUT_TEMPLATES'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private ParagraphExtractor paragraphExtractorMock

	private WikitextApi wikitextApiMock

	private AstronomicalObjectRepository astronomicalObjectRepositoryMock

	private AstronomicalObjectLinkWikitextProcessor astronomicalObjectLinkWikitextProcessor

	void setup() {
		paragraphExtractorMock = Mock()
		wikitextApiMock = Mock()
		astronomicalObjectRepositoryMock = Mock()
		astronomicalObjectLinkWikitextProcessor = new AstronomicalObjectLinkWikitextProcessor(paragraphExtractorMock, wikitextApiMock,
				astronomicalObjectRepositoryMock)
	}

	void "returns null when paragraphs list is empty"() {
		when:
		List<AstronomicalObject> astronomicalObjects = astronomicalObjectLinkWikitextProcessor.process(WIKITEXT)

		then:
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT) >> Lists.newArrayList()
		0 * _
		astronomicalObjects == []
	}

	void "processes links from first paragraph"() {
		given:
		PageLink pageLink = new PageLink(title: PAGE_LINK_TITLE, startPosition: 0)
		AstronomicalObject astronomicalObjectFoundByTitle = new AstronomicalObject()

		when:
		List<AstronomicalObject> astronomicalObjects = astronomicalObjectLinkWikitextProcessor.process(WIKITEXT)

		then:
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT) >> Lists.newArrayList(WIKITEXT)
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList(pageLink)
		1 * astronomicalObjectRepositoryMock.findByPageTitleWithPageMediaWikiSource(PAGE_LINK_TITLE, MEDIA_WIKI_SOURCE) >>
				Optional.of(astronomicalObjectFoundByTitle)
		0 * _
		astronomicalObjects[0] == astronomicalObjectFoundByTitle
	}

	void "processes links from first paragraph (line break with stop words)"() {
		given:
		PageLink pageLink = new PageLink(title: PAGE_LINK_TITLE, startPosition: 0)
		AstronomicalObject astronomicalObjectFoundByTitle = new AstronomicalObject()

		when:
		List<AstronomicalObject> astronomicalObjects = astronomicalObjectLinkWikitextProcessor.process(WITH_LINE_BREAK_WIKITEXT)

		then:
		1 * paragraphExtractorMock.extractParagraphs(WITH_LINE_BREAK_WIKITEXT) >> Lists.newArrayList(WIKITEXT)
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList(pageLink)
		1 * astronomicalObjectRepositoryMock.findByPageTitleWithPageMediaWikiSource(PAGE_LINK_TITLE, MEDIA_WIKI_SOURCE) >>
				Optional.of(astronomicalObjectFoundByTitle)
		0 * _
		astronomicalObjects[0] == astronomicalObjectFoundByTitle
	}

	void "returns empty list when no page was found by title"() {
		given:
		PageLink pageLink = new PageLink(title: PAGE_LINK_TITLE, startPosition: 0)

		when:
		List<AstronomicalObject> astronomicalObjects = astronomicalObjectLinkWikitextProcessor.process(WIKITEXT)

		then:
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT) >> Lists.newArrayList(WIKITEXT)
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList(pageLink)
		1 * astronomicalObjectRepositoryMock.findByPageTitleWithPageMediaWikiSource(PAGE_LINK_TITLE, MEDIA_WIKI_SOURCE) >>
				Optional.empty()
		0 * _
		astronomicalObjects == []
	}

	void "skips link when it produces NonUniqueResultException"() {
		given:
		PageLink pageLink = new PageLink(title: PAGE_LINK_TITLE)

		when:
		List<AstronomicalObject> astronomicalObjects = astronomicalObjectLinkWikitextProcessor.process(WIKITEXT)

		then:
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT) >> Lists.newArrayList(WIKITEXT)
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList(pageLink)
		1 * astronomicalObjectRepositoryMock.findByPageTitleWithPageMediaWikiSource(PAGE_LINK_TITLE, MEDIA_WIKI_SOURCE) >> { args ->
			throw new NonUniqueResultException()
		}
		0 * _
		astronomicalObjects == []
	}

	void "skips page link if it is preceded by 'light year'"() {
		given:
		PageLink pageLink = new PageLink(title: PAGE_LINK_TITLE, startPosition: 11)
		AstronomicalObject astronomicalObjectFoundByTitle = new AstronomicalObject()

		when:
		List<AstronomicalObject> astronomicalObjects = astronomicalObjectLinkWikitextProcessor.process(LIGTH_YEAR_WIKITEXT)

		then:
		1 * paragraphExtractorMock.extractParagraphs(LIGTH_YEAR_WIKITEXT) >> Lists.newArrayList(LIGTH_YEAR_WIKITEXT)
		1 * wikitextApiMock.getWikitextWithoutTemplates(LIGTH_YEAR_WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList(pageLink)
		1 * astronomicalObjectRepositoryMock.findByPageTitleWithPageMediaWikiSource(PAGE_LINK_TITLE, MEDIA_WIKI_SOURCE) >>
				Optional.of(astronomicalObjectFoundByTitle)
		0 * _
		astronomicalObjects == []
	}

	void "skips page link if it is preceded by 'visible from '"() {
		given:
		PageLink pageLink = new PageLink(title: PAGE_LINK_TITLE, startPosition: 13)
		AstronomicalObject astronomicalObjectFoundByTitle = new AstronomicalObject()

		when:
		List<AstronomicalObject> astronomicalObjects = astronomicalObjectLinkWikitextProcessor.process(VISILBE_FROM_WIKITEXT)

		then:
		1 * paragraphExtractorMock.extractParagraphs(VISILBE_FROM_WIKITEXT) >> Lists.newArrayList(VISILBE_FROM_WIKITEXT)
		1 * wikitextApiMock.getWikitextWithoutTemplates(VISILBE_FROM_WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList(pageLink)
		1 * astronomicalObjectRepositoryMock.findByPageTitleWithPageMediaWikiSource(PAGE_LINK_TITLE, MEDIA_WIKI_SOURCE) >>
				Optional.of(astronomicalObjectFoundByTitle)
		0 * _
		astronomicalObjects == []
	}

}
