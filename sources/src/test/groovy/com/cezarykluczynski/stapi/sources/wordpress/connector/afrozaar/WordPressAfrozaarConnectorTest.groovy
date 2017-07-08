package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar

import com.afrozaar.wordpress.wpapi.v2.Wordpress
import com.afrozaar.wordpress.wpapi.v2.model.Page
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse
import com.cezarykluczynski.stapi.sources.wordpress.api.enums.WordPressSource
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourceProperties
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourcesProperties
import spock.lang.Specification

class WordPressAfrozaarConnectorTest extends Specification {

	private static final String API_URL = 'API_URL'
	private static final String URI = 'URI'
	private static final Integer PAGE_NUMBER_INTEGER = 1
	private static final String PAGE_NUMBER_STRING = '1'

	private WordPressSourcesProperties wordPressSourcesPropertiesMock

	private WordpressFactory wordpressFactoryMock

	private WordPressAfrozaarConnector wordPressAfrozaarConnector

	void setup() {
		wordPressSourcesPropertiesMock = Mock()
		wordpressFactoryMock = Mock()
	}

	void "create client on connector construction"() {
		given:
		WordPressSourceProperties starTrekCardsWordPressSourceProperties = Mock()
		Wordpress wordpress = Mock()

		when:
		wordPressAfrozaarConnector = new WordPressAfrozaarConnector(wordPressSourcesPropertiesMock, wordpressFactoryMock)

		then:
		1 * wordPressSourcesPropertiesMock.starTrekCards >> starTrekCardsWordPressSourceProperties
		1 * starTrekCardsWordPressSourceProperties.apiUrl >> API_URL
		1 * wordpressFactoryMock.createForUrl(API_URL) >> wordpress
		0 * _
		wordPressAfrozaarConnector.wordpress == wordpress
	}

	void "gets paged response"() {
		given:
		WordPressSourceProperties starTrekCardsWordPressSourceProperties = Mock()
		starTrekCardsWordPressSourceProperties.apiUrl >> API_URL
		Wordpress wordpress = Mock()
		wordPressSourcesPropertiesMock.starTrekCards >> starTrekCardsWordPressSourceProperties
		wordpressFactoryMock.createForUrl(API_URL) >> wordpress
		wordPressAfrozaarConnector = new WordPressAfrozaarConnector(wordPressSourcesPropertiesMock, wordpressFactoryMock)
		PagedResponse<Page> pagedResponse = Mock()

		when:
		PagedResponse<Page> pagedResponseOutput = wordPressAfrozaarConnector.getPage(URI, PAGE_NUMBER_INTEGER, WordPressSource.STAR_TREK_CARDS)

		then:
		1 * wordpress.getPagedResponse(URI, Page, PAGE_NUMBER_STRING) >> pagedResponse
		0 * _
		pagedResponseOutput == pagedResponse
	}

}
