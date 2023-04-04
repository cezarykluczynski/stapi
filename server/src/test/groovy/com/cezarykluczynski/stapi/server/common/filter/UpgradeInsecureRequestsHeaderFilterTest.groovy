package com.cezarykluczynski.stapi.server.common.filter

import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.env.Environment
import spock.lang.Specification

class UpgradeInsecureRequestsHeaderFilterTest extends Specification {

	private Environment environmentMock

	private UpgradeInsecureRequestsHeaderFilter upgradeInsecureRequestsHeaderFilter

	void setup() {
		environmentMock = Mock()
		upgradeInsecureRequestsHeaderFilter = new UpgradeInsecureRequestsHeaderFilter(environmentMock)
	}

	void "does nothing when env variable is not set"() {
		given:
		ServletRequest request = Mock()
		ServletResponse response = Mock()
		FilterChain chain = Mock()

		when:
		upgradeInsecureRequestsHeaderFilter.init(null)

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_UPGRADE_INSECURE_REQUESTS) >> null
		0 * _

		when:
		upgradeInsecureRequestsHeaderFilter.doFilter(request, response, chain)

		then:
		1 * chain.doFilter(request, response)
		0 * _
	}

	void "redirects to HTTPS version of the site if header matches and request is a HTTP one"() {
		given:
		HttpServletRequest request = Mock()
		HttpServletResponse response = Mock()
		FilterChain chain = Mock()

		when:
		upgradeInsecureRequestsHeaderFilter.init(null)

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_UPGRADE_INSECURE_REQUESTS) >> 'true'
		0 * _

		when:
		upgradeInsecureRequestsHeaderFilter.doFilter(request, response, chain)

		then:
		1 * request.getHeader(UpgradeInsecureRequestsHeaderFilter.UPGRADE_INSECURE_REQUESTS_HEADER_NAME) >> '1'
		1 * request.getHeader(UpgradeInsecureRequestsHeaderFilter.FORWARDER_PROTO_HEADER_NAME) >> 'http'
		1 * request.scheme >> 'http'
		1 * request.requestURL >> new StringBuffer('http://stapi.co/about')
		1 * response.setHeader('Vary', UpgradeInsecureRequestsHeaderFilter.UPGRADE_INSECURE_REQUESTS_HEADER_NAME)
		1 * response.sendRedirect('https://stapi.co/about')
		0 * _
	}

	void "does no redirect when request is already HTTPS"() {
		given:
		HttpServletRequest request = Mock()
		HttpServletResponse response = Mock()
		FilterChain chain = Mock()

		when:
		upgradeInsecureRequestsHeaderFilter.init(null)

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_UPGRADE_INSECURE_REQUESTS) >> 'true'
		0 * _

		when:
		upgradeInsecureRequestsHeaderFilter.doFilter(request, response, chain)

		then:
		1 * request.getHeader(UpgradeInsecureRequestsHeaderFilter.UPGRADE_INSECURE_REQUESTS_HEADER_NAME) >> '1'
		1 * request.getHeader(UpgradeInsecureRequestsHeaderFilter.FORWARDER_PROTO_HEADER_NAME) >> 'http'
		1 * request.scheme >> 'https'
		1 * chain.doFilter(request, response)
		0 * _
	}

	void "does no redirect when request has invalid X-Forwarded-Proto header"() {
		given:
		HttpServletRequest request = Mock()
		HttpServletResponse response = Mock()
		FilterChain chain = Mock()

		when:
		upgradeInsecureRequestsHeaderFilter.init(null)

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_UPGRADE_INSECURE_REQUESTS) >> 'true'
		0 * _

		when:
		upgradeInsecureRequestsHeaderFilter.doFilter(request, response, chain)

		then:
		1 * request.getHeader(UpgradeInsecureRequestsHeaderFilter.UPGRADE_INSECURE_REQUESTS_HEADER_NAME) >> '1'
		1 * request.getHeader(UpgradeInsecureRequestsHeaderFilter.FORWARDER_PROTO_HEADER_NAME) >> 'https'
		1 * chain.doFilter(request, response)
		0 * _
	}

}
