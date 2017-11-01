package com.cezarykluczynski.stapi.server.oauth.github.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.oauth.github.endpoint.GitHubOAuthEndpoint
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class GitHubOAuthConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private GitHubOAuthConfiguration gitHubOAuthConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		gitHubOAuthConfiguration = new GitHubOAuthConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "GitHub Oauth REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = gitHubOAuthConfiguration.gitHubOAuthRestEndpoint()

		then:
		1 * endpointFactoryMock.createRestEndpoint(GitHubOAuthEndpoint, GitHubOAuthEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

}
