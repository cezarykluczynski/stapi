package com.cezarykluczynski.stapi.server.oauth.github.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.oauth.github.endpoint.GitHubOAuthEndpoint;
import org.apache.cxf.endpoint.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration("serverGitHubOAuthConfiguration")
public class GitHubOAuthConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server gitHubOAuthEndpoint() {
		return endpointFactory.createRestEndpoint(GitHubOAuthEndpoint.class, GitHubOAuthEndpoint.ADDRESS);
	}

}
