package com.cezarykluczynski.stapi.server.oauth.github.configuration;

import com.cezarykluczynski.stapi.etl.util.constant.FilterOrder;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.oauth.github.endpoint.GitHubOAuthEndpoint;
import com.cezarykluczynski.stapi.sources.oauth.github.session.OAuthSessionFilter;
import org.apache.cxf.endpoint.Server;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.inject.Inject;

@Configuration("serverGitHubOAuthConfiguration")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GitHubOAuthConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Inject
	private OAuthSessionFilter oauthSessionFilter;

	@Bean
	public Server gitHubOAuthRestEndpoint() {
		return endpointFactory.createRestEndpoint(GitHubOAuthEndpoint.class, GitHubOAuthEndpoint.ADDRESS);
	}

	@Bean
	public FilterRegistrationBean oauthSessionFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(oauthSessionFilter);
		filterRegistrationBean.addUrlPatterns("*");
		filterRegistrationBean.setOrder(FilterOrder.GITHUB_OAUTH);
		return filterRegistrationBean;
	}

}
