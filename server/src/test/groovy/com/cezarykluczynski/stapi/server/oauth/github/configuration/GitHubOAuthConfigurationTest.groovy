package com.cezarykluczynski.stapi.server.oauth.github.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.oauth.github.endpoint.GitHubOAuthEndpoint
import com.cezarykluczynski.stapi.sources.oauth.github.session.OAuthSessionFilter
import org.apache.cxf.endpoint.Server
import org.springframework.boot.web.servlet.FilterRegistrationBean
import spock.lang.Specification

class GitHubOAuthConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private OAuthSessionFilter oAuthSessionFilterMock

	private GitHubOAuthConfiguration gitHubOAuthConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		oAuthSessionFilterMock = Mock()
		gitHubOAuthConfiguration = new GitHubOAuthConfiguration(
				endpointFactory: endpointFactoryMock,
				oauthSessionFilter: oAuthSessionFilterMock)
	}

	void "GitHub Oauth REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = gitHubOAuthConfiguration.gitHubOAuthEndpoint()

		then:
		1 * endpointFactoryMock.createRestEndpoint(GitHubOAuthEndpoint, GitHubOAuthEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "FilterRegistrationBean is created for OAuthSessionFilter is created"() {
		when:
		FilterRegistrationBean filterRegistrationBean = gitHubOAuthConfiguration.filterRegistrationBean()

		then:
		filterRegistrationBean.filter == oAuthSessionFilterMock
		filterRegistrationBean.urlPatterns[0] == '*'
		filterRegistrationBean.order == 0
	}

}
