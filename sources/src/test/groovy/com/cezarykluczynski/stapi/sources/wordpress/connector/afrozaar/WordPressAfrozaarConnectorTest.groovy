package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar

import com.afrozaar.wordpress.wpapi.v2.Wordpress
import com.afrozaar.wordpress.wpapi.v2.model.Page
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse
import com.cezarykluczynski.stapi.sources.wordpress.api.enums.WordPressSource
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourceProperties
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourcesProperties
import com.cezarykluczynski.stapi.sources.wordpress.service.WordPressSourceConfigurationProvider
import org.springframework.http.converter.HttpMessageNotReadableException
import spock.lang.Specification

class WordPressAfrozaarConnectorTest extends Specification {

	private static final WordPressSource SOURCE = WordPressSource.STAR_TREK_CARDS
	private static final String API_URL = 'API_URL'
	private static final String PAGE_ID = 'PAGE_ID'
	private static final Integer PAGE_NUMBER_INTEGER = 1
	private static final String PAGE_NUMBER_STRING = '1'

	private WordPressSourcesProperties wordPressSourcesPropertiesMock

	private WordpressFactory wordpressFactoryMock

	private WordPressSourceConfigurationProvider wordPressSourceConfigurationProviderMock

	private WordPressAfrozaarConnector wordPressAfrozaarConnector

	void setup() {
		wordPressSourcesPropertiesMock = Mock()
		wordpressFactoryMock = Mock()
		wordPressSourceConfigurationProviderMock = Mock()
	}

	void "create client on connector construction"() {
		given:
		WordPressSourceProperties starTrekCardsWordPressSourceProperties = Mock()
		Wordpress wordpress = Mock()

		when:
		wordPressAfrozaarConnector = new WordPressAfrozaarConnector(wordPressSourcesPropertiesMock, wordpressFactoryMock,
				wordPressSourceConfigurationProviderMock)

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
		wordPressAfrozaarConnector = new WordPressAfrozaarConnector(wordPressSourcesPropertiesMock, wordpressFactoryMock,
				wordPressSourceConfigurationProviderMock)
		PagedResponse<Page> pagedResponse = Mock()
		WordPressSourceProperties wordPressSourceProperties = Mock()

		when:
		PagedResponse<Page> pagedResponseOutput = wordPressAfrozaarConnector.getPagesUnderPage(PAGE_ID, PAGE_NUMBER_INTEGER, SOURCE)

		then:
		1 * wordPressSourceConfigurationProviderMock.provideFor(SOURCE) >> wordPressSourceProperties
		1 * wordPressSourceProperties.minimalInterval >> 0
		1 * wordpress.getPagedResponse(WordPressAfrozaarConnector.PAGE_WITH_SLUG, Page, PAGE_ID, PAGE_NUMBER_STRING) >> pagedResponse
		0 * _
		pagedResponseOutput == pagedResponse
	}

	void "retries when exception was encountered"() {
		given:
		WordPressSourceProperties starTrekCardsWordPressSourceProperties = Mock()
		starTrekCardsWordPressSourceProperties.apiUrl >> API_URL
		Wordpress wordpress = Mock()
		wordPressSourcesPropertiesMock.starTrekCards >> starTrekCardsWordPressSourceProperties
		wordpressFactoryMock.createForUrl(API_URL) >> wordpress
		wordPressAfrozaarConnector = new WordPressAfrozaarConnector(wordPressSourcesPropertiesMock, wordpressFactoryMock,
				wordPressSourceConfigurationProviderMock)
		PagedResponse<Page> pagedResponse = Mock()
		WordPressSourceProperties wordPressSourceProperties = Mock()

		when:
		PagedResponse<Page> pagedResponseOutput = wordPressAfrozaarConnector.getPagesUnderPage(PAGE_ID, PAGE_NUMBER_INTEGER, SOURCE)

		then:
		1 * wordPressSourceConfigurationProviderMock.provideFor(SOURCE) >> wordPressSourceProperties
		1 * wordPressSourceProperties.minimalInterval >> 0
		1 * wordpress.getPagedResponse(WordPressAfrozaarConnector.PAGE_WITH_SLUG, Page, PAGE_ID, PAGE_NUMBER_STRING) >> {
			throw new HttpMessageNotReadableException('')
		}

		then:
		1 * wordPressSourceConfigurationProviderMock.provideFor(SOURCE) >> wordPressSourceProperties
		1 * wordPressSourceProperties.minimalInterval >> 0
		1 * wordpress.getPagedResponse(WordPressAfrozaarConnector.PAGE_WITH_SLUG, Page, PAGE_ID, PAGE_NUMBER_STRING) >> pagedResponse
		0 * _
		pagedResponseOutput == pagedResponse
	}

}
