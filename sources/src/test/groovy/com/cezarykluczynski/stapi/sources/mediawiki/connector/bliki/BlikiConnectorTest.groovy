package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiMinimalIntervalProvider
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourceProperties
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties
import com.google.common.collect.Maps
import info.bliki.api.Connector
import org.apache.commons.io.IOUtils
import org.apache.http.*
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import spock.lang.Specification

class BlikiConnectorTest extends Specification {

	private static final String XML = '<?xml version="1.0"?><root></root>'
	private static final Long INTERVAL = 500L
	private static final String TITLE = 'TITLE'
	private static final String ACTION_URL = 'ACTION_URL'

	private BlikiUserDecoratorBeanMapProvider blikiUserDecoratorBeanMapProviderMock

	private MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProviderMock

	private MediaWikiSourcesProperties mediaWikiSourcesProperties

	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN = MediaWikiSource.MEMORY_ALPHA_EN
	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_BETA_EN = MediaWikiSource.MEMORY_BETA_EN

	private HttpClientBuilder httpClientBuilderMock

	private BlikiConnector blikiConnector

	def setup() {
		blikiUserDecoratorBeanMapProviderMock = Mock(BlikiUserDecoratorBeanMapProvider)
		mediaWikiMinimalIntervalProviderMock = Mock(MediaWikiMinimalIntervalProvider)
		mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				memoryAlphaEn: new MediaWikiSourceProperties(),
				memoryBetaEn: new MediaWikiSourceProperties()
		)

		httpClientBuilderMock = Mock(HttpClientBuilder) {
			build() >> Mock(CloseableHttpClient) {
				execute(*_) >> Mock(CloseableHttpResponse) {
					getStatusLine() >> Mock(StatusLine) {
						getStatusCode() >> HttpStatus.SC_OK
					}
					getEntity() >> Mock(HttpEntity) {
						getContentType() >> Mock(Header) {
							getElements() >> [
									Mock(HeaderElement) {
										getName() >> 'text/xml'
										getParameters() >> []
									}
							]
						}
						getContent() >>> [IOUtils.toInputStream(XML), IOUtils.toInputStream(XML)]
						getContentLength() >>> [XML.length(), XML.length()]
					}

				}
			}
		}

		blikiConnector = new BlikiConnector(blikiUserDecoratorBeanMapProviderMock, mediaWikiMinimalIntervalProviderMock,
				mediaWikiSourcesProperties)
	}

	def "reads page for Memory Alpha EN"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock(UserDecorator)
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_ALPHA_EN, userDecorator)

		when: 'page is requested'
		String xml = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: 'page is returned'
		1 * blikiUserDecoratorBeanMapProviderMock.getUserEnumMap() >> mediaWikiSourceUserDecoratorMap
		1 * mediaWikiMinimalIntervalProviderMock.getMemoryAlphaEnInterval() >> INTERVAL
		1 * userDecorator.getConnector() >> connector
		2 * userDecorator.getActionUrl() >> ACTION_URL
		xml == XML

		when: 'another page is requested'
		String xml2 = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another page is returned,  but call is postponed'
		1 * blikiUserDecoratorBeanMapProviderMock.getUserEnumMap() >> mediaWikiSourceUserDecoratorMap
		1 * mediaWikiMinimalIntervalProviderMock.getMemoryAlphaEnInterval() >> INTERVAL
		1 * userDecorator.getConnector() >> connector
		2 * userDecorator.getActionUrl() >> ACTION_URL
		startInMilliseconds + INTERVAL <= endInMillis
		xml2 == XML
	}

	def "reads XML for Memory Alpha EN"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock(UserDecorator)
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_ALPHA_EN, userDecorator)

		when: 'xml is requested'
		String xml = blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: 'xml is returned'
		1 * blikiUserDecoratorBeanMapProviderMock.getUserEnumMap() >> mediaWikiSourceUserDecoratorMap
		1 * mediaWikiMinimalIntervalProviderMock.getMemoryAlphaEnInterval() >> INTERVAL
		1 * userDecorator.getConnector() >> connector
		2 * userDecorator.getActionUrl() >> ACTION_URL
		xml == XML

		when: 'another xml is requested'
		String xml2 = blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another xml is returned, but call is postponed'
		1 * blikiUserDecoratorBeanMapProviderMock.getUserEnumMap() >> mediaWikiSourceUserDecoratorMap
		1 * mediaWikiMinimalIntervalProviderMock.getMemoryAlphaEnInterval() >> INTERVAL
		1 * userDecorator.getConnector() >> connector
		2 * userDecorator.getActionUrl() >> ACTION_URL
		startInMilliseconds + INTERVAL <= endInMillis
		xml2 == XML
	}

	def "reads page for Memory Beta EN"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock(UserDecorator)
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_BETA_EN, userDecorator)

		when: 'page is requested'
		String xml = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)

		then: 'page is returned'
		1 * blikiUserDecoratorBeanMapProviderMock.getUserEnumMap() >> mediaWikiSourceUserDecoratorMap
		1 * mediaWikiMinimalIntervalProviderMock.getMemoryBetaEnInterval() >> INTERVAL
		1 * userDecorator.getConnector() >> connector
		2 * userDecorator.getActionUrl() >> ACTION_URL
		xml == XML

		when: 'another page is requested'
		String xml2 = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another page is returned,  but call is postponed'
		1 * blikiUserDecoratorBeanMapProviderMock.getUserEnumMap() >> mediaWikiSourceUserDecoratorMap
		1 * mediaWikiMinimalIntervalProviderMock.getMemoryBetaEnInterval() >> INTERVAL
		1 * userDecorator.getConnector() >> connector
		2 * userDecorator.getActionUrl() >> ACTION_URL
		startInMilliseconds + INTERVAL <= endInMillis
		xml2 == XML
	}

	def "reads XML for Memory Beta EN"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock(UserDecorator)
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_BETA_EN, userDecorator)

		when: 'xml is requested'
		String xml = blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)

		then: 'xml is returned'
		1 * blikiUserDecoratorBeanMapProviderMock.getUserEnumMap() >> mediaWikiSourceUserDecoratorMap
		1 * mediaWikiMinimalIntervalProviderMock.getMemoryBetaEnInterval() >> INTERVAL
		1 * userDecorator.getConnector() >> connector
		2 * userDecorator.getActionUrl() >> ACTION_URL
		xml == XML

		when: 'another xml is requested'
		String xml2 = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another xml is returned, but call is postponed'
		1 * blikiUserDecoratorBeanMapProviderMock.getUserEnumMap() >> mediaWikiSourceUserDecoratorMap
		1 * mediaWikiMinimalIntervalProviderMock.getMemoryBetaEnInterval() >> INTERVAL
		1 * userDecorator.getConnector() >> connector
		2 * userDecorator.getActionUrl() >> ACTION_URL
		startInMilliseconds + INTERVAL <= endInMillis
		xml2 == XML
	}


	def "throws RuntimeError on any error"() {
		given: 'connector without sendXML method is injected'
		UserDecorator userDecorator = Mock(UserDecorator) {
			getConnector() >> Mock(Connector)
		}
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_ALPHA_EN, userDecorator)

		when:
		blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then:
		thrown(RuntimeException)
	}

}
