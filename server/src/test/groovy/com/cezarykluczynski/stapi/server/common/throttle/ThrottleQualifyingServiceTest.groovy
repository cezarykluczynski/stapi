package com.cezarykluczynski.stapi.server.common.throttle

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class ThrottleQualifyingServiceTest extends Specification {

	private HttpServletRequest httpServletRequestMock

	private ThrottleQualifyingService throttleQualifyingService

	void setup() {
		httpServletRequestMock = Mock()
		throttleQualifyingService = new ThrottleQualifyingService(httpServletRequestMock)
	}

	void "returns true when it is request shorter than common"() {
		when:
		boolean qualifiedForThrottle = throttleQualifyingService.qualifiedForThrottle

		then:
		1 * httpServletRequestMock.requestURI >> '/api/v1/rest/short'
		0 * _
		qualifiedForThrottle
	}

	void "returns true when it is not request to common REST endpoint"() {
		when:
		boolean qualifiedForThrottle = throttleQualifyingService.qualifiedForThrottle

		then:
		2 * httpServletRequestMock.requestURI >> '/api/v1/rest/astronomicalObject/search'
		0 * _
		qualifiedForThrottle
	}

	void "returns false when it is request to common REST endpoint"() {
		when:
		boolean qualifiedForThrottle = throttleQualifyingService.qualifiedForThrottle

		then:
		2 * httpServletRequestMock.requestURI >> '/api/v1/rest/common/mappings'
		0 * _
		!qualifiedForThrottle
	}

}
