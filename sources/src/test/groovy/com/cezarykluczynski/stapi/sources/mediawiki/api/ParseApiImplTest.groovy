package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.http.connector.HttpConnector
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourceProperties
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import org.apache.http.HttpEntity
import org.apache.http.NameValuePair
import spock.lang.Specification

class ParseApiImplTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String LOCAL_API_URL = 'http://localhost:9761/local_wiki/api.php'
	private static final String LOCAL_EXPAND_TEMPLATES_URL = 'http://localhost:9761/local_wiki/index.php/Special:ExpandTemplates'
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

	private HttpConnector httpConnectorMock

	void setup() {
		httpConnectorMock = Mock()
	}

	void "sets correct url to Wikipedia's special page"() {
		given:
		MediaWikiSourcesProperties mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				technicalHelper: new MediaWikiSourceProperties(apiUrl: WIKIPEDIA_API_URL))

		when:
		ParseApiImpl parseApiImpl = new ParseApiImpl(httpConnectorMock, mediaWikiSourcesProperties)

		then:
		parseApiImpl.expandTemplatesUrl == WIKIPEDIA_EXPAND_TEMPLATES_URL
	}

	void "throws exception when api url is null"() {
		given:
		MediaWikiSourcesProperties mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				technicalHelper: new MediaWikiSourceProperties())

		when:
		new ParseApiImpl(httpConnectorMock, mediaWikiSourcesProperties)

		then:
		thrown(StapiRuntimeException)
	}

	void "throws exception when api url is malformed"() {
		given:
		MediaWikiSourcesProperties mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				technicalHelper: new MediaWikiSourceProperties(apiUrl: MALFORMED_URL))

		when:
		new ParseApiImpl(httpConnectorMock, mediaWikiSourcesProperties)

		then:
		thrown(StapiRuntimeException)
	}

	void "gets parse tree"() {
		given:
		MediaWikiSourcesProperties mediaWikiSourcesProperties = new MediaWikiSourcesProperties(
				technicalHelper: new MediaWikiSourceProperties(apiUrl: LOCAL_API_URL))
		ParseApiImpl parseApiImpl = new ParseApiImpl(httpConnectorMock, mediaWikiSourcesProperties)
		HttpEntity httpEntity = Mock()

		when:
		String parseTree = parseApiImpl.parseWikitextToXmlTree(WIKITEXT)

		then:
		1 * httpConnectorMock.post(LOCAL_EXPAND_TEMPLATES_URL, _ as List) >> { String url, List<NameValuePair> params ->
			params[0].name == 'wpInput'
			params[0].value == WIKITEXT
			params[1].name == 'wpRemoveComments'
			params[1].value == '1'
			params[2].name == 'wpGenerateXml'
			params[2].value == '1'
			params[3].name == 'wpEditToken'
			params[3].value == '+\\'
			params[4].name == 'title'
			params[4].value == 'Special:ExpandTemplates'
			httpEntity
		}
		httpEntity.content >> new ByteArrayInputStream(BODY.bytes)
		0 * _
		parseTree == '<root></root>'
	}

}
