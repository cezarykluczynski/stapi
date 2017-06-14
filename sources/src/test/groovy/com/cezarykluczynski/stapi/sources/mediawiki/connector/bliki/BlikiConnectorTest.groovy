package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiMinimalIntervalProvider
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourceProperties
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties
import com.cezarykluczynski.stapi.sources.mediawiki.service.wikia.WikiaWikisDetector
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Maps
import info.bliki.api.Connector
import org.apache.commons.io.IOUtils
import org.apache.http.Header
import org.apache.http.HeaderElement
import org.apache.http.HttpEntity
import org.apache.http.HttpStatus
import org.apache.http.StatusLine
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import spock.lang.Specification

class BlikiConnectorTest extends Specification {

	private static final String XML = '<?xml version="1.0"?><root></root>'
	private static final Long INTERVAL = 500L
	private static final String TITLE = 'TITLE'
	private static final String ACTION_URL = 'ACTION_URL'

	private BlikiUserDecoratorBeanMapProvider blikiUserDecoratorBeanMapProviderMock

	private WikiaWikisDetector wikiaWikisDetector

	private MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProviderMock

	private MediaWikiSourcesProperties mediaWikiSourcesProperties

	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN = MediaWikiSource.MEMORY_ALPHA_EN
	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_BETA_EN = MediaWikiSource.MEMORY_BETA_EN

	private HttpClientBuilder httpClientBuilderMock

	private CloseableHttpClient closeableHttpClient

	private CloseableHttpResponse closeableHttpResponse

	private BlikiConnector blikiConnector

	void setup() {
		blikiUserDecoratorBeanMapProviderMock = Mock()
		wikiaWikisDetector = Mock()
		mediaWikiMinimalIntervalProviderMock = Mock()
		mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				memoryAlphaEn: new MediaWikiSourceProperties(),
				memoryBetaEn: new MediaWikiSourceProperties()
		)

		StatusLine statusLine = Mock()
		statusLine.statusCode >> HttpStatus.SC_OK
		HeaderElement headerElement = Mock()
		headerElement.name >> 'text/xml'
		headerElement.parameters >> []
		Header header = Mock()
		header.elements >> [headerElement]

		HttpEntity httpEntity = Mock()
		httpEntity.contentType >> header
		httpEntity.content >>> [IOUtils.toInputStream(XML), IOUtils.toInputStream(XML)]
		httpEntity.contentLength >>> [XML.length(), XML.length()]

		closeableHttpResponse = Mock()
		closeableHttpResponse.entity >> httpEntity

		closeableHttpResponse.statusLine >> statusLine
		closeableHttpResponse.entity >> httpEntity

		closeableHttpClient = Mock()
		closeableHttpClient.execute(*_) >> closeableHttpResponse

		httpClientBuilderMock = Mock()
		httpClientBuilderMock.build() >> closeableHttpClient

		blikiConnector = new BlikiConnector(blikiUserDecoratorBeanMapProviderMock, wikiaWikisDetector, mediaWikiMinimalIntervalProviderMock,
				mediaWikiSourcesProperties)
	}

	void "reads page from Memory Alpha EN"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock()
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_ALPHA_EN, userDecorator)

		when: 'page is requested'
		String xml = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: 'page is returned'
		1 * wikiaWikisDetector.isWikiaWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML

		when: 'another page is requested'
		String xml2 = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another page is returned,  but call is postponed'
		1 * wikiaWikisDetector.isWikiaWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		startInMilliseconds + INTERVAL <= endInMillis
		xml2 == XML
	}

	void "reads XML from Memory Alpha EN"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock()
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_ALPHA_EN, userDecorator)

		when: 'xml is requested'
		String xml = blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: 'xml is returned'
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML

		when: 'another xml is requested'
		String xml2 = blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another xml is returned, but call is postponed'
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		startInMilliseconds + INTERVAL <= endInMillis
		xml2 == XML
	}

	void "reads page for Memory Beta EN"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock()
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_BETA_EN, userDecorator)

		when: 'page is requested'
		String xml = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)

		then: 'page is returned'
		1 * wikiaWikisDetector.isWikiaWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryBetaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML

		when: 'another page is requested'
		String xml2 = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another page is returned,  but call is postponed'
		1 * wikiaWikisDetector.isWikiaWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryBetaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		startInMilliseconds + INTERVAL <= endInMillis
		xml2 == XML
	}

	void "reads XML for Memory Beta EN"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock()
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_BETA_EN, userDecorator)

		when: 'xml is requested'
		String xml = blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)

		then: 'xml is returned'
		1 * mediaWikiMinimalIntervalProviderMock.memoryBetaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML

		when: 'another xml is requested'
		String xml2 = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another xml is returned, but call is postponed'
		1 * wikiaWikisDetector.isWikiaWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryBetaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		startInMilliseconds + INTERVAL <= endInMillis
		xml2 == XML
	}

	void "do pass parsetree in props when url is not Wikia's"() {
		given:
		closeableHttpClient = Mock()
		httpClientBuilderMock = Mock()
		httpClientBuilderMock.build() >> closeableHttpClient

		blikiConnector = new BlikiConnector(blikiUserDecoratorBeanMapProviderMock, wikiaWikisDetector, mediaWikiMinimalIntervalProviderMock,
				mediaWikiSourcesProperties)

		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock()
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_ALPHA_EN, userDecorator)

		when: 'page is requested'
		blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: 'page is returned, and URL is not Wikia\'s wiki'
		1 * wikiaWikisDetector.isWikiaWiki(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		1 * closeableHttpClient.execute(_ as HttpGet) >> { HttpGet httpGet ->
			assert httpGet.URI.query.contains('parsetree')
			closeableHttpResponse
		}
		2 * userDecorator.actionUrl >> ACTION_URL

		when: 'page is requested'
		blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: 'page is returned, and URL is Wikia\'s wiki'
		1 * wikiaWikisDetector.isWikiaWiki(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> true
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		1 * closeableHttpClient.execute(_ as HttpGet) >> { HttpGet httpGet ->
			assert !httpGet.URI.query.contains('parsetree')
			closeableHttpResponse
		}
		2 * userDecorator.actionUrl >> ACTION_URL
	}

	void "gets page info from Memory Alpha EN"() {
		given:
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_ALPHA_EN, userDecorator)

		when: 'page info is requested'
		String xml = blikiConnector.getPageInfo(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then:
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorBeanMapProviderMock.userEnumMap >> mediaWikiSourceUserDecoratorMap
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML
	}

	void "throws StapiRuntimeError on any error"() {
		given: 'connector without sendXML method is injected'
		Connector connector = Mock()
		UserDecorator userDecorator = Mock()
		userDecorator.connector >> connector
		Map<MediaWikiSource, UserDecorator> mediaWikiSourceUserDecoratorMap = Maps.newHashMap()
		mediaWikiSourceUserDecoratorMap.put(MediaWikiSource.MEMORY_ALPHA_EN, userDecorator)

		when:
		blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then:
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		thrown(StapiRuntimeException)
	}

}
