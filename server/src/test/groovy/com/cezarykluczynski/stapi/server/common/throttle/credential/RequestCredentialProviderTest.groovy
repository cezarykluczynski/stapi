package com.cezarykluczynski.stapi.server.common.throttle.credential

import org.apache.cxf.message.Message
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class RequestCredentialProviderTest extends Specification {

	private static final String IP_ADDRESS = 'IP_ADDRESS'
	private static final String API_KEY = 'API_KEY'
	private static final String SOAP_CONTENT = 'SOAP_CONTENT'

	private HttpServletRequest httpServletRequestMock

	private MessageContentExtractor messageContentExtractorMock

	private SoapApiKeyExtractor soapApiKeyExtractorMock

	private RestApiKeyExtractor restApiKeyExtractorMock

	private RequestCredentialProvider requestCredentialProvider

	void setup() {
		httpServletRequestMock = Mock()
		messageContentExtractorMock = Mock()
		soapApiKeyExtractorMock = Mock()
		restApiKeyExtractorMock = Mock()
		requestCredentialProvider = new RequestCredentialProvider(httpServletRequestMock, messageContentExtractorMock, soapApiKeyExtractorMock,
				restApiKeyExtractorMock)
	}

	void "creates RequestCredential with IP address"() {
		given:
		Message message = Mock()

		when:
		RequestCredential requestCredential = requestCredentialProvider.provideRequestCredential(message)

		then:
		1 * httpServletRequestMock.requestURI >> ''
		1 * httpServletRequestMock.remoteAddr >> IP_ADDRESS
		0 * _
		requestCredential.ipAddress == IP_ADDRESS
		requestCredential.apiKey == null
		requestCredential.requestCredentialType == RequestCredentialType.IP_ADDRESS
	}

	void "creates RequestCredential REST API key"() {
		given:
		Message message = Mock()

		when:
		RequestCredential requestCredential = requestCredentialProvider.provideRequestCredential(message)

		then:
		1 * httpServletRequestMock.requestURI >> '/rest/'
		1 * restApiKeyExtractorMock.extract(httpServletRequestMock) >> API_KEY
		1 * httpServletRequestMock.remoteAddr >> IP_ADDRESS
		0 * _
		requestCredential.ipAddress == IP_ADDRESS
		requestCredential.apiKey == API_KEY
		requestCredential.requestCredentialType == RequestCredentialType.API_KEY
	}

	void "creates RequestCredential SOAP API key"() {
		given:
		Message message = Mock()

		when:
		RequestCredential requestCredential = requestCredentialProvider.provideRequestCredential(message)

		then:
		1 * httpServletRequestMock.requestURI >> '/soap/'
		1 * messageContentExtractorMock.extract(message) >> SOAP_CONTENT
		1 * soapApiKeyExtractorMock.extract(SOAP_CONTENT) >> API_KEY
		1 * httpServletRequestMock.remoteAddr >> IP_ADDRESS
		0 * _
		requestCredential.ipAddress == IP_ADDRESS
		requestCredential.apiKey == API_KEY
		requestCredential.requestCredentialType == RequestCredentialType.API_KEY
	}

}
