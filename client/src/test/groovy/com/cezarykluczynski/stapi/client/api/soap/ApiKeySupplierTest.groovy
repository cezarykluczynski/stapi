package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.ApiRequest
import spock.lang.Specification

class ApiKeySupplierTest extends Specification {

	private static final String API_KEY_1 = 'API_KEY_1'
	private static final String API_KEY_2 = 'API_KEY_2'

	private ApiKeySupplier apiKeySupplier

	void setup() {
		apiKeySupplier = new ApiKeySupplier(API_KEY_1)
	}

	void "when API key is null in request, it is supplied"() {
		given:
		ApiRequest apiRequest = new ApiRequest()

		when:
		apiKeySupplier.supply(apiRequest)

		then:
		apiRequest.apiKey == API_KEY_1
	}

	void "when API key is not null in request, API key is not updated"() {
		given:
		ApiRequest apiRequest = new ApiRequest(apiKey: API_KEY_2)

		when:
		apiKeySupplier.supply(apiRequest)

		then:
		apiRequest.apiKey == API_KEY_2
	}

}
