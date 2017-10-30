package com.cezarykluczynski.stapi.server.security.configuration;

import com.cezarykluczynski.stapi.etl.util.constant.FilterOrder;
import com.cezarykluczynski.stapi.server.security.filter.CsfrFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class SecurityConfiguration {

	@Inject
	private CsfrFilter csfrFilter;

	@Bean
	public FilterRegistrationBean csfrFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(csfrFilter);
		filterRegistrationBean.addUrlPatterns("*");
		filterRegistrationBean.setOrder(FilterOrder.CSFR);
		return filterRegistrationBean;
	}

}
