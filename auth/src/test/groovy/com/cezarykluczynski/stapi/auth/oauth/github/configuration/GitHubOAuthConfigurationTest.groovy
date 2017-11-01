package com.cezarykluczynski.stapi.auth.oauth.github.configuration

import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import spock.lang.Specification

class GitHubOAuthConfigurationTest extends Specification {

	private OAuthSessionFilter oAuthSessionFilterMock

	private GitHubOAuthConfiguration gitHubOAuthConfiguration

	void setup() {
		oAuthSessionFilterMock = Mock()
		gitHubOAuthConfiguration = new GitHubOAuthConfiguration(oauthSessionFilter: oAuthSessionFilterMock)
	}

	void "FilterRegistrationBean is created for OAuthSessionFilter is created"() {
		when:
		FilterRegistrationBean filterRegistrationBean = gitHubOAuthConfiguration.oauthSessionFilterRegistrationBean()

		then:
		filterRegistrationBean.filter == oAuthSessionFilterMock
		filterRegistrationBean.urlPatterns[0] == '*'
		filterRegistrationBean.order == 0
	}

}
