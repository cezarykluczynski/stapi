package com.cezarykluczynski.stapi.server.security.configuration;

import com.cezarykluczynski.stapi.etl.util.constant.FilterOrder;
import com.cezarykluczynski.stapi.server.security.filter.OriginVerifyingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;

import javax.inject.Inject;

@Configuration
public class SecurityConfiguration {

	public static final String CSFR_SENSITIVE_PATH = "/rest/panel/";
	private static final String STAR = "*";

	@Inject
	private OriginVerifyingFilter originVerifyingFilter;

	@Inject
	private PermissionEvaluator permissionEvaluatorImpl;

	@Bean
	public FilterRegistrationBean originVerifyingFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(originVerifyingFilter);
		filterRegistrationBean.addUrlPatterns(STAR);
		filterRegistrationBean.setOrder(FilterOrder.ORIGIN_VERIFIER);
		return filterRegistrationBean;
	}

	@Bean
	public DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
		DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
		defaultMethodSecurityExpressionHandler.setPermissionEvaluator(permissionEvaluatorImpl);
		return defaultMethodSecurityExpressionHandler;
	}

}
