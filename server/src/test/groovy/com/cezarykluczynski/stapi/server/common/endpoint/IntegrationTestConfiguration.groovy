package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.RequestContextFilter
import spock.lang.Specification

@Configuration
class IntegrationTestConfiguration extends Specification {

	@Bean
	OAuthSessionHolder oAuthSessionHolder() {
		Mock(OAuthSessionHolder)
	}

	@Bean
	FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean()
		filterRegistrationBean.setFilter(new RequestContextFilter())
		filterRegistrationBean.addUrlPatterns('*')
		filterRegistrationBean
	}

}
