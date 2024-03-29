package com.cezarykluczynski.stapi.etl.mediawiki.connector.bliki

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.configuration.MediaWikiMinimalIntervalProvider
import com.cezarykluczynski.stapi.etl.mediawiki.configuration.MediaWikiSourceProperties
import com.cezarykluczynski.stapi.etl.mediawiki.configuration.MediaWikiSourcesProperties
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.etl.mediawiki.service.fandom.FandomWikisDetector
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
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
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class BlikiConnectorTest extends Specification {

	private static final String XML = '<?xml version="1.0"?><root></root>'
	private static final Long INTERVAL = 500L
	private static final String TITLE = 'TITLE'
	private static final String API_URL = 'API_URL'
	private static final String ACTION_URL = 'ACTION_URL'
	private static final String CATEGORY_TITLE = 'CATEGORY_TITLE'
	private static final String RAW_RESULT = 'RAW_RESULT'
	private static final String CATEGORY_JSON = '{"categorytree":{"*":"<div class=\\"CategoryTreeSection\\">' +
			'<div class=\\"CategoryTreeItem\\"><span class=\\"CategoryTreeEmptyBullet\\"></span>' +
			'<a href=\\"/wiki/Category:Animal_performers\\" title=\\"Category:Animal performers\\">Animal performers</a></div>' +
			'<div class=\\"CategoryTreeChildren\\" style=\\"display:none\\"></div></div><div class=\\"CategoryTreeSection\\">' +
			'<div class=\\"CategoryTreeItem\\"><span class=\\"CategoryTreeEmptyBullet\\"></span> ' +
			'<a href=\\"/wiki/Category:Audiobook_performers\\" title=\\"Category:Audiobook performers\\">Audiobook performers</a></div>' +
			'</div>"}}'
	private BlikiUserDecoratorFactory blikiUserDecoratorFactoryMock

	private FandomWikisDetector fandomWikisDetector

	private MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProviderMock

	private MediaWikiSourcesProperties mediaWikiSourcesProperties

	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN = MediaWikiSource.MEMORY_ALPHA_EN
	private static final MediaWikiSource MEDIA_WIKI_SOURCE_MEMORY_BETA_EN = MediaWikiSource.MEMORY_BETA_EN

	private HttpClientBuilder httpClientBuilderMock

	private CloseableHttpClient closeableHttpClient

	private CloseableHttpResponse closeableHttpResponse

	private RestTemplate restTemplateMock

	private BlikiConnector blikiConnector

	void setup() {
		blikiUserDecoratorFactoryMock = Mock()
		fandomWikisDetector = Mock()
		mediaWikiMinimalIntervalProviderMock = Mock()
		mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				memoryAlphaEn: new MediaWikiSourceProperties(apiUrl: API_URL),
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

		restTemplateMock = Mock()

		blikiConnector = new BlikiConnector(blikiUserDecoratorFactoryMock, fandomWikisDetector, mediaWikiMinimalIntervalProviderMock,
				mediaWikiSourcesProperties, restTemplateMock)
	}

	void "reads page from Memory Alpha EN"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()
		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock()

		when: 'page is requested'
		String xml = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: 'page is returned'
		1 * fandomWikisDetector.isFandomWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> userDecorator
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML

		when: 'another page is requested'
		String xml2 = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another page is returned,  but call is postponed'
		1 * fandomWikisDetector.isFandomWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> userDecorator
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

		when: 'xml is requested'
		String xml = blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: 'xml is returned'
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> userDecorator
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML

		when: 'another xml is requested'
		String xml2 = blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another xml is returned, but call is postponed'
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> userDecorator
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

		when: 'page is requested'
		String xml = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)

		then: 'page is returned'
		1 * fandomWikisDetector.isFandomWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryBetaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_BETA_EN) >> userDecorator
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML

		when: 'another page is requested'
		String xml2 = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another page is returned, but call is postponed'
		1 * fandomWikisDetector.isFandomWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryBetaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_BETA_EN) >> userDecorator
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

		when: 'xml is requested'
		String xml = blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)

		then: 'xml is returned'
		1 * mediaWikiMinimalIntervalProviderMock.memoryBetaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_BETA_EN) >> userDecorator
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML

		when: 'another xml is requested'
		String xml2 = blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_BETA_EN)
		long endInMillis = System.currentTimeMillis()

		then: 'another xml is returned, but call is postponed'
		1 * fandomWikisDetector.isFandomWiki(_) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryBetaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_BETA_EN) >> userDecorator
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		startInMilliseconds + INTERVAL <= endInMillis
		xml2 == XML
	}

	void "do pass parsetree in props when url is not Fandom's"() {
		given:
		closeableHttpClient = Mock()
		httpClientBuilderMock = Mock()
		httpClientBuilderMock.build() >> closeableHttpClient

		blikiConnector = new BlikiConnector(blikiUserDecoratorFactoryMock, fandomWikisDetector, mediaWikiMinimalIntervalProviderMock,
				mediaWikiSourcesProperties, restTemplateMock)

		Connector connector = new Connector(httpClientBuilderMock)
		UserDecorator userDecorator = Mock()

		when: 'page is requested'
		blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: "page is returned, and URL is not Fandom's wiki"
		1 * fandomWikisDetector.isFandomWiki(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> false
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> userDecorator
		1 * userDecorator.connector >> connector
		1 * closeableHttpClient.execute(_ as HttpGet) >> { HttpGet httpGet ->
			assert httpGet.URI.query.contains('parsetree')
			closeableHttpResponse
		}
		2 * userDecorator.actionUrl >> ACTION_URL

		when: 'page is requested'
		blikiConnector.getPage(TITLE, MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then: "page is returned, and URL is Fandom's wiki"
		1 * fandomWikisDetector.isFandomWiki(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> true
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> userDecorator
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
		1 * blikiUserDecoratorFactoryMock.createFor(MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN) >> userDecorator
		1 * userDecorator.connector >> connector
		2 * userDecorator.actionUrl >> ACTION_URL
		xml == XML
	}

	void "gets categories from Memory Alpha EN"() {
		given:
		String url = "$API_URL?action=categorytree&category=$CATEGORY_TITLE&format=json&options={options}"
		int depth = 2

		when:
		List<CategoryHeader> categoryHeaders = blikiConnector.getCategories(CATEGORY_TITLE, MediaWikiSource.MEMORY_ALPHA_EN, depth)

		then:
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * restTemplateMock.getForEntity(url, String, "{\"depth\":$depth}") >> ResponseEntity.ok(CATEGORY_JSON)
		0 * _
		categoryHeaders == [new CategoryHeader(title: 'Animal_performers'), new CategoryHeader(title: 'Audiobook_performers')]
	}

	void "gets raw template transclusions from Memory Alpha EN"() {
		given:
		String ticontinue = '234'
		String url = "$API_URL?action=query&prop=transcludedin&tilimit=500&tinamespace=0&titles=Template:${TemplateTitle.SIDEBAR_ORGANIZATION}" +
				"&format=json&ticontinue=$ticontinue"

		when:
		String rawResult = blikiConnector.getRawTemplateTransclusions(TemplateTitle.SIDEBAR_ORGANIZATION, ticontinue,
				MediaWikiSource.MEMORY_ALPHA_EN)

		then:
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		1 * restTemplateMock.getForEntity(url, String) >> ResponseEntity.ok(RAW_RESULT)
		0 * _
		rawResult == RAW_RESULT
	}

	void "throws StapiRuntimeError on any error"() {
		given: 'connector without sendXML method is injected'
		Connector connector = Mock()
		UserDecorator userDecorator = Mock()
		userDecorator.connector >> connector

		when:
		blikiConnector.readXML(Maps.newHashMap(), MEDIA_WIKI_SOURCE_MEMORY_ALPHA_EN)

		then:
		1 * mediaWikiMinimalIntervalProviderMock.memoryAlphaEnInterval >> INTERVAL
		thrown(StapiRuntimeException)
	}

}
