package com.cezarykluczynski.stapi.auth.oauth.session

import com.cezarykluczynski.stapi.util.constant.ApplicationPermission
import com.google.common.collect.Lists
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class OAuthSessionFilterTest extends Specification {

	private OAuthSessionHolder oauthSessionHolderMock

	private OAuthSessionFilter oAuthSessionFilter

	void setup() {
		oauthSessionHolderMock = Mock()
		oAuthSessionFilter = new OAuthSessionFilter(oauthSessionHolderMock)
	}

	void setupSpec() {
		SecurityContextHolder.context.authentication = null
	}

	void cleanupSpec() {
		SecurityContextHolder.context.authentication = null
	}

	void "does nothing when init method is called"() {
		given:
		FilterConfig filterConfig = Mock()

		when:
		oAuthSessionFilter.init(filterConfig)

		then:
		0 * _
	}

	void "when OAuthSession is not found, null is put into SecurityContext"() {
		given:
		ServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()

		when:
		oAuthSessionFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * oauthSessionHolderMock.nullableOAuthSession >> null
		1 * filterChain.doFilter(servletRequest, servletResponse)
		0 * _
		SecurityContextHolder.context.authentication != null
		SecurityContextHolder.context.authentication.principal == null
		SecurityContextHolder.context.authentication.authorities.empty
	}

	void "when OAuthSession is found, it is put into SecurityContext"() {
		given:
		ServletRequest servletRequest = Mock()
		ServletResponse servletResponse = Mock()
		FilterChain filterChain = Mock()
		OAuthSession oAuthSession = new OAuthSession(permissions: Lists.newArrayList(ApplicationPermission.API_KEY_MANAGEMENT))

		when:
		oAuthSessionFilter.doFilter(servletRequest, servletResponse, filterChain)

		then:
		1 * oauthSessionHolderMock.nullableOAuthSession >> oAuthSession
		1 * filterChain.doFilter(servletRequest, servletResponse)
		0 * _
		SecurityContextHolder.context.authentication != null
		SecurityContextHolder.context.authentication.principal == oAuthSession
		SecurityContextHolder.context.authentication.authorities[0].authority == ApplicationPermission.API_KEY_MANAGEMENT
	}

	void "does nothing when destroy method is called"() {
		when:
		oAuthSessionFilter.destroy()

		then:
		0 * _
	}

}
