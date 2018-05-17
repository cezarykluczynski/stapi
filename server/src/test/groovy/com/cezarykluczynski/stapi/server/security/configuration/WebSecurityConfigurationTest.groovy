package com.cezarykluczynski.stapi.server.security.configuration

import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType
import com.google.common.collect.Maps
import org.springframework.security.config.annotation.ObjectPostProcessor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.LazyCsrfTokenRepository
import spock.lang.Specification

class WebSecurityConfigurationTest extends Specification {

	private FeatureSwitchApi featureSwitchApiMock

	private SensitivePathsRequestMatcher sensitivePathsRequestMatcherMock

	private WebSecurityConfiguration webSecurityConfiguration

	void setup() {
		featureSwitchApiMock = Mock()
		sensitivePathsRequestMatcherMock = Mock()
		webSecurityConfiguration = new WebSecurityConfiguration(
				featureSwitchApi: featureSwitchApiMock,
				sensitivePathsRequestMatcher: sensitivePathsRequestMatcherMock)
	}

	void "when admin panel is enabled, HttpSecurity is configured with CSRF tokens"() {
		given:
		ObjectPostProcessor objectPostProcessor = Mock()
		AuthenticationManagerBuilder authenticationManagerBuilder = Mock()
		Map<Class<? extends Object>, Object> sharedObjects = Maps.newHashMap()
		HttpSecurity httpSecurity = new HttpSecurity(objectPostProcessor, authenticationManagerBuilder, sharedObjects)

		when:
		webSecurityConfiguration.configure(httpSecurity)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.ADMIN_PANEL) >> true
		0 * _
		httpSecurity.csrf().csrfTokenRepository instanceof CookieCsrfTokenRepository
		CookieCsrfTokenRepository cookieCsrfTokenRepository = (CookieCsrfTokenRepository) httpSecurity.csrf().csrfTokenRepository
		!cookieCsrfTokenRepository.cookieHttpOnly
		httpSecurity.csrf().requireCsrfProtectionMatcher == sensitivePathsRequestMatcherMock
		httpSecurity.getConfigurers(HttpBasicConfigurer).size() == 1
		httpSecurity.getConfigurers(ExpressionUrlAuthorizationConfigurer).size() == 1
		httpSecurity.sessionManagement().sessionCreationPolicy == SessionCreationPolicy.IF_REQUIRED
	}

	void "when admin panel is disabled, HttpSecurity is configured sessions or CSRF tokens"() {
		given:
		ObjectPostProcessor objectPostProcessor = Mock()
		AuthenticationManagerBuilder authenticationManagerBuilder = Mock()
		Map<Class<? extends Object>, Object> sharedObjects = Maps.newHashMap()
		HttpSecurity httpSecurity = new HttpSecurity(objectPostProcessor, authenticationManagerBuilder, sharedObjects)

		when:
		webSecurityConfiguration.configure(httpSecurity)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.ADMIN_PANEL) >> false
		0 * _
		httpSecurity.csrf().csrfTokenRepository instanceof LazyCsrfTokenRepository
		httpSecurity.sessionManagement().sessionCreationPolicy == SessionCreationPolicy.NEVER
	}

}
