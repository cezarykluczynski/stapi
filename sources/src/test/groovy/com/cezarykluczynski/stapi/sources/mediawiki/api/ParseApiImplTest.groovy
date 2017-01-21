package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourceProperties
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.Parameter
import org.mockserver.model.ParameterBody
import spock.lang.Shared
import spock.lang.Specification

class ParseApiImplTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String LOCAL_API_URL = 'http://localhost:9761/local_wiki/api.php'
	private static final String WIKIPEDIA_API_URL = 'https://en.wikipedia.org/w/api.php'
	private static final String WIKIPEDIA_EXPAND_TEMPLATES_URL = 'https://en.wikipedia.org/wiki/Special:ExpandTemplates'
	private static final String MALFORMED_URL = 'MALFORMED_URL'
	private static final String BODY = '''
<html lang="en" dir="ltr" class="client-js">
	<head>
		<meta charset="UTF-8">
	</head>
	<body>
		<div>
			<textarea name="output" id="output" cols="10" rows="10" readonly="readonly">&lt;root&gt;&lt;/root&gt;</textarea>
			<textarea name="output" id="output" cols="10" rows="10" readonly="readonly">Some content</textarea>
		</div>
	</body>
</html>
'''

	@Shared
	private ClientAndServer mockServer

	private MockServerClient mockServerClient

	void setupSpec() {
		mockServer = ClientAndServer.startClientAndServer(9761)
	}

	void cleanupSpec() {
		mockServer.stop()
	}

	void setup() {
		mockServerClient = new MockServerClient('localhost', 9761)
	}

	void "sets correct url to Wikipedia's special page"() {
		given:
		MediaWikiSourcesProperties mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				technicalHelper: new MediaWikiSourceProperties(apiUrl: WIKIPEDIA_API_URL))

		when:
		ParseApiImpl parseApiImpl = new ParseApiImpl(mediaWikiSourcesProperties)

		then:
		parseApiImpl.expandTemplatesUrl == WIKIPEDIA_EXPAND_TEMPLATES_URL
	}

	void "throws exception when api url is null"() {
		given:
		MediaWikiSourcesProperties mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				technicalHelper: new MediaWikiSourceProperties())

		when:
		new ParseApiImpl(mediaWikiSourcesProperties)

		then:
		thrown(RuntimeException)
	}

	void "throws exception when api url is malformed"() {
		given:
		MediaWikiSourcesProperties mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				technicalHelper: new MediaWikiSourceProperties(apiUrl: MALFORMED_URL))

		when:
		new ParseApiImpl(mediaWikiSourcesProperties)

		then:
		thrown(RuntimeException)
	}

	void "gets parse tree"() {
		given:
		mockServerClient
				.when(HttpRequest
						.request()
						.withMethod('POST')
						.withBody(new ParameterBody(
								new Parameter('wpInput', WIKITEXT),
								new Parameter('wpRemoveComments', '1'),
								new Parameter('wpGenerateXml', '1'),
								new Parameter('wpEditToken', '+\\'),
								new Parameter('title', 'Special:ExpandTemplates')
						))
						.withPath('/local_wiki/index.php/Special:ExpandTemplates')
				)
				.respond(HttpResponse.response().withStatusCode(200).withBody(BODY))

		MediaWikiSourcesProperties mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				technicalHelper: new MediaWikiSourceProperties(apiUrl: LOCAL_API_URL))
		ParseApiImpl parseApiImpl = new ParseApiImpl(mediaWikiSourcesProperties)

		when:
		String parseTree = parseApiImpl.parseWikitextToXmlTree(WIKITEXT)

		then:
		parseTree == '<root></root>'
	}

}
