package com.cezarykluczynski.stapi.server.common.throttle.credential

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class RequestCredentialProviderTest extends Specification {

	private static final String IP_ADDRESS = 'IP_ADDRESS'

	private HttpServletRequest httpServletRequestMock

	private RequestCredentialProvider requestCredentialProvider

	void setup() {
		httpServletRequestMock = Mock(HttpServletRequest)
		requestCredentialProvider = new RequestCredentialProvider(httpServletRequestMock)
	}

	void "creates RequestCredential with IP address"() {
		when:
		RequestCredential requestCredential = requestCredentialProvider.provideRequestCredential()

		then:
		1 * httpServletRequestMock.remoteAddr >> IP_ADDRESS
		0 * _
		requestCredential.ipAddress == IP_ADDRESS
		requestCredential.apiKey == null
		requestCredential.requestCredentialType == RequestCredentialType.IP_ADDRESS
	}

}
