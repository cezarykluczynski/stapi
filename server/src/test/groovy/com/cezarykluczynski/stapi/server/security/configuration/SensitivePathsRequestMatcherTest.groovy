package com.cezarykluczynski.stapi.server.security.configuration

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class SensitivePathsRequestMatcherTest extends Specification {

	private static final String SENSITIVE_PATH = SecurityConfiguration.CSFR_SENSITIVE_PATH
	private static final String NONSENSITIVE_PATH = '/some/path'

	private SensitivePathsRequestMatcher sensitivePathsRequestMatcher

	void setup() {
		sensitivePathsRequestMatcher = new SensitivePathsRequestMatcher()
	}

	void "returns true for sensitive path"() {
		given:
		HttpServletRequest httpServletRequest = Mock()

		when:
		boolean result = sensitivePathsRequestMatcher.matches(httpServletRequest)

		then:
		1 * httpServletRequest.pathInfo >> SENSITIVE_PATH
		0 * _
		result
	}

	void "returns false for non-sensitive path"() {
		given:
		HttpServletRequest httpServletRequest = Mock()

		when:
		boolean result = sensitivePathsRequestMatcher.matches(httpServletRequest)

		then:
		1 * httpServletRequest.pathInfo >> NONSENSITIVE_PATH
		0 * _
		!result
	}

}
