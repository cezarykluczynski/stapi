package com.cezarykluczynski.stapi.server.oauth.github.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.oauth.github.endpoint.GitHubOAuthEndpoint;
import com.cezarykluczynski.stapi.sources.oauth.github.session.OAuthSessionFilter;
import org.apache.cxf.endpoint.Server;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.inject.Inject;

@Configuration("serverGitHubOAuthConfiguration")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GitHubOAuthConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Inject
	private OAuthSessionFilter oauthSessionFilter;

	@Bean
	public Server gitHubOAuthEndpoint() {
		return endpointFactory.createRestEndpoint(GitHubOAuthEndpoint.class, GitHubOAuthEndpoint.ADDRESS);
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(oauthSessionFilter);
		filterRegistrationBean.addUrlPatterns("*");
		filterRegistrationBean.setOrder(0);
		return filterRegistrationBean;
	}

}
