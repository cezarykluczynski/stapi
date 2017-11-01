package com.cezarykluczynski.stapi.server.security.filter

import com.cezarykluczynski.stapi.server.security.configuration.SecurityConfiguration
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class OriginVerifyingFilterTest extends Specification {

	private static final String PATHINFO_NO_ACTION = '/pathinfo/no/action'
	private static final String PATHINFO_SENSITIVE = SecurityConfiguration.CSFR_SENSITIVE_PATH
	private static final String DOMAIN_1 = 'domain1'
	private static final String DOMAIN_1_RAW = 'http://domain1:8080/'
	private static final String DOMAIN_2 = 'domain2'
	private static final String DOMAIN_2_RAW = 'http://domain2:8080/'
	private static final String DOMAIN_2_UNPARSABLE_HOST = 'http://DOMAIN_2:8080/'
	private static final String DOMAIN_2_INVALID = ':DOMAIN_2'

	private OriginVerifyingFilter originVerifyingFilter

	void setup() {
		originVerifyingFilter = new OriginVerifyingFilter()
	}

	void "does nothing when init method is called"() {
		given:
		FilterConfig filterConfig = Mock()

		when:
		originVerifyingFilter.init(filterConfig)

		then:
		0 * _
	}

	void "when request does not touches sensitive paths, nothing happens"() {
		given:
		HttpServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		originVerifyingFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * servletRequest.pathInfo >> null
		1 * filterChain.doFilter(servletRequest, servletResponse)
		0 * _
	}

	void "when pathinfo is not sensitive, nothing happens"() {
		given:
		HttpServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		originVerifyingFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * servletRequest.pathInfo >> PATHINFO_NO_ACTION
		1 * filterChain.doFilter(servletRequest, servletResponse)
		0 * _
	}

	void "when pathinfo is sensitive, and origin matches server name, nothing happens"() {
		given:
		HttpServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		originVerifyingFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * servletRequest.pathInfo >> PATHINFO_SENSITIVE
		1 * servletRequest.serverName >> DOMAIN_1
		1 * servletRequest.getHeader(OriginVerifyingFilter.ORIGIN_HEADER_NAME) >> DOMAIN_1_RAW
		1 * filterChain.doFilter(servletRequest, servletResponse)
		0 * _
	}

	void "when pathinfo is sensitive, and origin does not match server name, exception is thrown"() {
		given:
		HttpServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		originVerifyingFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * servletRequest.pathInfo >> PATHINFO_SENSITIVE
		1 * servletRequest.serverName >> DOMAIN_1
		1 * servletRequest.getHeader(OriginVerifyingFilter.ORIGIN_HEADER_NAME) >> DOMAIN_2_RAW
		0 * _
		thrown(StapiRuntimeException)
	}

	void "when pathinfo is sensitive, and origin host could not be extracted, exception is thrown"() {
		given:
		HttpServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		originVerifyingFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * servletRequest.pathInfo >> PATHINFO_SENSITIVE
		1 * servletRequest.serverName >> DOMAIN_2
		1 * servletRequest.getHeader(OriginVerifyingFilter.ORIGIN_HEADER_NAME) >> DOMAIN_2_UNPARSABLE_HOST
		0 * _
		thrown(StapiRuntimeException)
	}

	void "when pathinfo is sensitive, and URL cannot be parsed, exception is thrown"() {
		given:
		HttpServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		originVerifyingFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * servletRequest.pathInfo >> PATHINFO_SENSITIVE
		1 * servletRequest.serverName >> DOMAIN_2
		1 * servletRequest.getHeader(OriginVerifyingFilter.ORIGIN_HEADER_NAME) >> DOMAIN_2_INVALID
		0 * _
		thrown(StapiRuntimeException)
	}

	void "does nothing when destroy method is called"() {
		when:
		originVerifyingFilter.destroy()

		then:
		0 * _
	}

}
