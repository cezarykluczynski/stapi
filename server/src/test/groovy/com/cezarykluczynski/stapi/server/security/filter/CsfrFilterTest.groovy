package com.cezarykluczynski.stapi.server.security.filter

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class CsfrFilterTest extends Specification {

	private static final String PATHINFO_NO_ACTION = '/pathinfo/no/action'
	private static final String PATHINFO_SENSITIVE = RandomUtil.randomItem(Lists.newArrayList(CsfrFilter.CSFR_SENSITIVE_PATHS))
	private static final String DOMAIN_1 = 'DOMAIN_1'
	private static final String DOMAIN_2 = 'DOMAIN_2'

	private CsfrFilter csfrFilter

	void setup() {
		csfrFilter = new CsfrFilter()
	}

	void "does nothing when init method is called"() {
		given:
		FilterConfig filterConfig = Mock()

		when:
		csfrFilter.init(filterConfig)

		then:
		0 * _
	}

	void "when request does not touches sensitive paths, nothing happens"() {
		given:
		HttpServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		csfrFilter.doFilter(servletRequest, servletResponse, filterChain)

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
		csfrFilter.doFilter(servletRequest, servletResponse, filterChain)

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
		csfrFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * servletRequest.pathInfo >> PATHINFO_SENSITIVE
		1 * servletRequest.serverName >> DOMAIN_1
		1 * servletRequest.getHeader(CsfrFilter.ORIGIN_HEADER_NAME) >> DOMAIN_1
		1 * filterChain.doFilter(servletRequest, servletResponse)
		0 * _
	}

	void "when pathinfo is sensitive, and origin does not match server name, exception is thrown"() {
		given:
		HttpServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		csfrFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * servletRequest.pathInfo >> PATHINFO_SENSITIVE
		1 * servletRequest.serverName >> DOMAIN_1
		1 * servletRequest.getHeader(CsfrFilter.ORIGIN_HEADER_NAME) >> DOMAIN_2
		0 * _
		thrown(StapiRuntimeException)
	}

	void "does nothing when destroy method is called"() {
		when:
		csfrFilter.destroy()

		then:
		0 * _
	}

}
