package com.cezarykluczynski.stapi.server.common.throttle.credential

import spock.lang.Specification

class SoapApiKeyExtractorTest extends Specification {

	private static final String API_KEY = 'API_KEY'

	private static final String XML_WITH_API_KEY = createRequestString('<apiKey>' + API_KEY + '</apiKey>\n')
	private static final String XML_WITHOUT_API_KEY_VALUE = createRequestString('<apiKey></apiKey>\n')
	private static final String XML_WITHOUT_API_KEY_NODE = createRequestString('')

	private SoapApiKeyExtractor soapApiKeyExtractor

	void setup() {
		soapApiKeyExtractor = new SoapApiKeyExtractor()
	}

	void "gets API key from valid XML"() {
		when:
		String apiKey = soapApiKeyExtractor.extract(XML_WITH_API_KEY)

		then:
		apiKey == API_KEY
	}

	void "returns null when API key node is empty"() {
		when:
		String apiKey = soapApiKeyExtractor.extract(XML_WITHOUT_API_KEY_VALUE)

		then:
		apiKey == null
	}

	void "returns null when API key node is missing"() {
		when:
		String apiKey = soapApiKeyExtractor.extract(XML_WITHOUT_API_KEY_NODE)

		then:
		apiKey == null
	}

	private static String createRequestString(String apiKeyNodeContent) {
		'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" ' +
				'xmlns:per="http://stapi.co/api/v1/soap/performer">\n' +
				'	<soapenv:Header/>\n' +
				'	<soapenv:Body>\n' +
				'		<per:PerformerFullRequest>\n' +
				'			' + apiKeyNodeContent +
				'			<per:uid>PEMA0000026721</per:uid>\n' +
				'		</per:PerformerFullRequest>\n' +
				'	</soapenv:Body>\n' +
				'</soapenv:Envelope>'
	}

}
