package com.cezarykluczynski.stapi.sources.wordpress.api

import com.afrozaar.wordpress.wpapi.v2.model.Page as WordPressPage
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse
import com.cezarykluczynski.stapi.sources.wordpress.api.enums.WordPressSource
import com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar.WordPressAfrozaarConnector
import com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar.WordPressPageMapper
import com.cezarykluczynski.stapi.sources.wordpress.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class WordPressApiTest extends Specification {

	private static final WordPressSource WORD_PRESS_SOURCE = WordPressSource.STAR_TREK_CARDS
	private static final String PAGE_ID = 'PAGE_ID'

	private WordPressAfrozaarConnector wordPressAfrozaarConnectorMock

	private WordPressPageMapper wordPressPageMapperMock

	private WordPressApi wordPressApi

	void setup() {
		wordPressAfrozaarConnectorMock = Mock()
		wordPressPageMapperMock = Mock()
		wordPressApi = new WordPressApi(wordPressAfrozaarConnectorMock, wordPressPageMapperMock)
	}

	void "gets all pages from a WordPress API"() {
		given:
		WordPressPage wordPressPage1 = Mock()
		WordPressPage wordPressPage2 = Mock()
		Page page1 = Mock()
		Page page2 = Mock()
		PagedResponse<WordPressPage> pagePagedResponse1 = new PagedResponse<>(WordPressPage, '', '', '', 2, Lists.newArrayList(wordPressPage1))
		PagedResponse<WordPressPage> pagePagedResponse2 = new PagedResponse<>(WordPressPage, '', '', null, 2, Lists.newArrayList(wordPressPage2))
		PagedResponse<WordPressPage> pagePagedResponse3 = new PagedResponse<>(WordPressPage, '', '', null, 2, Lists.newArrayList())

		when:
		List<Page> pageList = wordPressApi.getAllPagesUnderPage(PAGE_ID, WordPressSource.STAR_TREK_CARDS)

		then:
		1 * wordPressAfrozaarConnectorMock.getPagesUnderPage(PAGE_ID, 1, WORD_PRESS_SOURCE) >> pagePagedResponse1
		1 * wordPressAfrozaarConnectorMock.getPagesUnderPage(PAGE_ID, 2, WORD_PRESS_SOURCE) >> pagePagedResponse2
		1 * wordPressAfrozaarConnectorMock.getPagesUnderPage(PAGE_ID, 3, WORD_PRESS_SOURCE) >> pagePagedResponse3
		1 * wordPressPageMapperMock.map(wordPressPage1) >> page1
		1 * wordPressPageMapperMock.map(wordPressPage2) >> page2
		0 * _
		pageList.size() == 2
		pageList.contains page1
		pageList.contains page2
	}

}
