package com.cezarykluczynski.stapi.server.security.configuration;

import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.inject.Inject;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Inject
	private FeatureSwitchApi featureSwitchApi;

	@Inject
	private SensitivePathsRequestMatcher sensitivePathsRequestMatcher;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		HttpSecurity httpSecurity;

		if (featureSwitchApi.isEnabled(FeatureSwitchType.ADMIN_PANEL)) {
			httpSecurity = http
					.csrf()
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					.requireCsrfProtectionMatcher(sensitivePathsRequestMatcher)
					.and();
		} else {
			httpSecurity = http
					.csrf()
					.disable()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.NEVER)
					.and();
		}

		httpSecurity
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers("/actuator/**")
				.authenticated();
	}

}
