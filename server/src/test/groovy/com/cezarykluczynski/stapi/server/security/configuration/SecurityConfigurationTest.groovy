package com.cezarykluczynski.stapi.server.security.configuration

import com.cezarykluczynski.stapi.server.security.filter.CsfrFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import spock.lang.Specification

class SecurityConfigurationTest extends Specification {

	private CsfrFilter csfrFilterMock

	private SecurityConfiguration securityConfiguration

	void setup() {
		csfrFilterMock = Mock()
		securityConfiguration = new SecurityConfiguration(csfrFilter: csfrFilterMock)
	}

	void "FilterRegistrationBean is created for CsfrFilter is created"() {
		when:
		FilterRegistrationBean filterRegistrationBean = securityConfiguration.csfrFilterRegistrationBean()

		then:
		filterRegistrationBean.filter == csfrFilterMock
		filterRegistrationBean.urlPatterns[0] == '*'
		filterRegistrationBean.order == -1
	}

}
