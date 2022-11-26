package com.cezarykluczynski.stapi.server.oauth.github.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.oauth.github.endpoint.GitHubOAuthEndpoint;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.apache.cxf.endpoint.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.inject.Inject;

@Configuration("serverGitHubOAuthConfiguration")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile(SpringProfile.AUTH)
public class GitHubOAuthConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server gitHubOAuthRestEndpoint() {
		return endpointFactory.createRestEndpoint(GitHubOAuthEndpoint.class, GitHubOAuthEndpoint.ADDRESS);
	}

}
