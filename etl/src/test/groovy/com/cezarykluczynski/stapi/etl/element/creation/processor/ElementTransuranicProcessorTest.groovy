package com.cezarykluczynski.stapi.etl.element.creation.processor

import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.constant.PageTitle
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Lists
import spock.lang.Specification

class ElementTransuranicProcessorTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private PageApi pageApiMock

	private WikitextApi wikitextApiMock

	private PageSectionExtractor pageSectionExtractorMock

	private ElementTransuranicProcessor elementTransuranicProcessor

	void setup() {
		pageApiMock = Mock()
		wikitextApiMock = Mock()
		pageSectionExtractorMock = Mock()
		elementTransuranicProcessor = new ElementTransuranicProcessor(pageApiMock, wikitextApiMock, pageSectionExtractorMock)
	}

	void "when page is not found, exception is thrown"() {
		when:
		elementTransuranicProcessor.isTransuranic(TITLE_1)

		then:
		1 * pageApiMock.getPage(PageTitle.TRANSURANIC, MediaWikiSource.MEMORY_ALPHA_EN) >> null
		StapiRuntimeException stapiRuntimeException = thrown()
		stapiRuntimeException.message == "Cannot get page ${PageTitle.TRANSURANIC}"
	}

	void "when page section is not found, exception is thrown"() {
		given:
		Page page = Mock()

		when:
		elementTransuranicProcessor.isTransuranic(TITLE_1)

		then:
		1 * pageApiMock.getPage(PageTitle.TRANSURANIC, MediaWikiSource.MEMORY_ALPHA_EN) >> page
		1 * pageSectionExtractorMock.findByTitles(page, ElementTransuranicProcessor.LIST_OF_TRANSURANIC_ELEMENTS) >> Lists.newArrayList()
		0 * _
		StapiRuntimeException stapiRuntimeException = thrown()
		stapiRuntimeException.message == "Cannot get page section ${ElementTransuranicProcessor.LIST_OF_TRANSURANIC_ELEMENTS}"
	}

	void "when page and page section are found first time, they are used from now on to decide whether page title is transuranic"() {
		given:
		Page page = Mock()
		PageSection pageSection = new PageSection(wikitext: WIKITEXT)

		when:
		boolean isTitle1Transuranic = elementTransuranicProcessor.isTransuranic(TITLE_1)

		then:
		1 * pageApiMock.getPage(PageTitle.TRANSURANIC, MediaWikiSource.MEMORY_ALPHA_EN) >> page
		1 * pageSectionExtractorMock.findByTitles(page, ElementTransuranicProcessor.LIST_OF_TRANSURANIC_ELEMENTS) >> Lists.newArrayList(pageSection)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(TITLE_2)
		0 * _
		!isTitle1Transuranic

		when:
		boolean isTitle2Transuranic = elementTransuranicProcessor.isTransuranic(TITLE_2)

		then:
		0 * _
		isTitle2Transuranic
	}

}
