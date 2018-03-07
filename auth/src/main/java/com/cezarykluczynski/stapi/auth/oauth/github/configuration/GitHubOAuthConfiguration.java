package com.cezarykluczynski.stapi.auth.oauth.github.configuration;

import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionFilter;
import com.cezarykluczynski.stapi.util.constant.FilterOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration("sourcesGitHubOAuthConfiguration")
@EnableConfigurationProperties({GitHubOAuthProperties.class})
public class GitHubOAuthConfiguration {

	@Inject
	private OAuthSessionFilter oauthSessionFilter;

	@Bean
	@ConditionalOnProperty("featureSwitch.userPanel")
	public FilterRegistrationBean oauthSessionFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(oauthSessionFilter);
		filterRegistrationBean.addUrlPatterns("*");
		filterRegistrationBean.setOrder(FilterOrder.GITHUB_OAUTH);
		return filterRegistrationBean;
	}

}
