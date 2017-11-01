package com.cezarykluczynski.stapi.server.security.configuration

import com.google.common.collect.Maps
import org.springframework.security.config.annotation.ObjectPostProcessor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import spock.lang.Specification

class WebSecurityConfigurationTest extends Specification {

	private SensitivePathsRequestMatcher sensitivePathsRequestMatcherMock

	private WebSecurityConfiguration webSecurityConfiguration

	void setup() {
		sensitivePathsRequestMatcherMock = Mock()
		webSecurityConfiguration = new WebSecurityConfiguration(sensitivePathsRequestMatcher: sensitivePathsRequestMatcherMock)
	}

	void "HttpSecurity is configured"() {
		given:
		ObjectPostProcessor objectPostProcessor = Mock()
		AuthenticationManagerBuilder authenticationManagerBuilder = Mock()
		Map sharedObjects = Maps.newHashMap()
		HttpSecurity httpSecurity = new HttpSecurity(objectPostProcessor, authenticationManagerBuilder, sharedObjects)

		when:
		webSecurityConfiguration.configure(httpSecurity)

		then:
		0 * _
		httpSecurity.csrf().csrfTokenRepository instanceof CookieCsrfTokenRepository
		CookieCsrfTokenRepository cookieCsrfTokenRepository = (CookieCsrfTokenRepository) httpSecurity.csrf().csrfTokenRepository
		!cookieCsrfTokenRepository.cookieHttpOnly
		httpSecurity.csrf().requireCsrfProtectionMatcher == sensitivePathsRequestMatcherMock
	}

}
