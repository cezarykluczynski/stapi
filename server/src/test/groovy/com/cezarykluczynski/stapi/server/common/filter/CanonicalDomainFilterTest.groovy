package com.cezarykluczynski.stapi.server.common.filter

import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.env.Environment
import spock.lang.Specification

class CanonicalDomainFilterTest extends Specification {

	private Environment environmentMock

	private CanonicalDomainFilter canonicalDomainFilter

	void setup() {
		environmentMock = Mock()
		canonicalDomainFilter = new CanonicalDomainFilter(environmentMock)
	}

	void "does nothing when env variable is not set"() {
		given:
		ServletRequest request = Mock()
		ServletResponse response = Mock()
		FilterChain chain = Mock()

		when:
		canonicalDomainFilter.init(null)

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN) >> null
		0 * _

		when:
		canonicalDomainFilter.doFilter(request, response, chain)

		then:
		1 * chain.doFilter(request, response)
		0 * _
	}

	void "does nothing when domains match"() {
		given:
		HttpServletRequest request = Mock()
		ServletResponse response = Mock()
		FilterChain chain = Mock()

		when:
		canonicalDomainFilter.init(null)

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN) >> 'stapi.co'
		0 * _

		when:
		canonicalDomainFilter.doFilter(request, response, chain)

		then:
		1 * request.requestURL >> new StringBuffer('https://stapi.co/clients')
		1 * chain.doFilter(request, response)
		0 * _
	}

	void "throws exception when domains does not match"() {
		given:
		HttpServletRequest request = Mock()
		ServletResponse response = Mock()
		FilterChain chain = Mock()

		when:
		canonicalDomainFilter.init(null)

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN) >> 'stapi.co'
		0 * _

		when:
		canonicalDomainFilter.doFilter(request, response, chain)

		then:
		1 * request.requestURL >> new StringBuffer('http://some-redirect.com/api-overview')
		0 * _
		thrown(RuntimeException)
	}

}
