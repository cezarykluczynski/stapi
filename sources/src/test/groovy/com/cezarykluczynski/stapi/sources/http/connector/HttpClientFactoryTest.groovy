package com.cezarykluczynski.stapi.sources.http.connector

import spock.lang.Specification

class HttpClientFactoryTest extends Specification {

	private HttpClientFactory httpClientFactory

	void setup() {
		httpClientFactory = new HttpClientFactory()
	}

	void "creates HttpClient"() {
		expect:
		httpClientFactory.create() != null
	}

}
