package com.cezarykluczynski.stapi.server.common.throttle.credential

import com.google.common.collect.Maps
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class RestApiKeyExtractorTest extends Specification {

	private static final String API_KEY = 'API_KEY'

	private RestApiKeyExtractor restApiKeyExtractor

	void setup() {
		restApiKeyExtractor = new RestApiKeyExtractor()
	}

	void "gets API key from parameter map"() {
		given:
		HttpServletRequest httpServletRequest = Mock()
		Map<String, String[]> parameterMap = Maps.newHashMap()
		String[] parameters = [API_KEY]
		parameterMap.put(RequestCredential.API_KEY, parameters)

		when:
		String apiKey = restApiKeyExtractor.extract(httpServletRequest)

		then:
		1 * httpServletRequest.parameterMap >> parameterMap
		0 * _
		apiKey == API_KEY
	}

	void "returns null when no API key could be found"() {
		given:
		HttpServletRequest httpServletRequest = Mock()
		Map<String, String[]> parameterMap = Maps.newHashMap()

		when:
		String apiKey = restApiKeyExtractor.extract(httpServletRequest)

		then:
		1 * httpServletRequest.parameterMap >> parameterMap
		0 * _
		apiKey == null
	}

}
