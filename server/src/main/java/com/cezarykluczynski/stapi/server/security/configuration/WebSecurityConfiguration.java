package com.cezarykluczynski.stapi.server.security.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.inject.Inject;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Inject
	private SensitivePathsRequestMatcher sensitivePathsRequestMatcher;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.requireCsrfProtectionMatcher(sensitivePathsRequestMatcher)
				.and()
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers("/actuator/**")
				.authenticated();
	}

}
