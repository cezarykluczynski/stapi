package com.cezarykluczynski.stapi.server.common.metrics.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import spock.lang.Specification

class RestEndpointApiBrowserFilterTest extends Specification {

	RestEndpointApiBrowserFilter restEndpointApiBrowserFilter

	void setup() {
		restEndpointApiBrowserFilter = new RestEndpointApiBrowserFilter()
	}

	void "sets API Browser flag"() {
		given:
		HttpServletRequest httpServletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		restEndpointApiBrowserFilter.doFilter(httpServletRequest, servletResponse, filterChain)

		then:
		1 * httpServletRequest.getHeader('X-Api-Browser') >> 'true'
		1 * filterChain.doFilter(httpServletRequest, servletResponse)
		0 * _
		restEndpointApiBrowserFilter.apiBrowser
	}

	void "does not set API Browser flag when header is missing"() {
		given:
		HttpServletRequest httpServletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		restEndpointApiBrowserFilter.doFilter(httpServletRequest, servletResponse, filterChain)

		then:
		1 * httpServletRequest.getHeader('X-Api-Browser') >> null
		1 * filterChain.doFilter(httpServletRequest, servletResponse)
		0 * _
		!restEndpointApiBrowserFilter.apiBrowser
	}

	void "does not set API Browser flag when ServletRequest is not HttpServletRequest"() {
		given:
		ServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		restEndpointApiBrowserFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * filterChain.doFilter(servletRequest, servletResponse)
		0 * _
		!restEndpointApiBrowserFilter.apiBrowser
	}

}
