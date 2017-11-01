package com.cezarykluczynski.stapi.server.security.configuration

import com.cezarykluczynski.stapi.server.security.filter.OriginVerifyingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import spock.lang.Specification

class SecurityConfigurationTest extends Specification {

	private OriginVerifyingFilter originVerifyingFilterMock

	private PermissionEvaluatorImpl permissionEvaluatorImplMock

	private SecurityConfiguration securityConfiguration

	void setup() {
		originVerifyingFilterMock = Mock()
		permissionEvaluatorImplMock = Mock()
		securityConfiguration = new SecurityConfiguration(
				originVerifyingFilter: originVerifyingFilterMock,
				permissionEvaluatorImpl: permissionEvaluatorImplMock)
	}

	void "FilterRegistrationBean is created for OriginVerifyingFilter is created"() {
		when:
		FilterRegistrationBean filterRegistrationBean = securityConfiguration.originVerifyingFilterRegistrationBean()

		then:
		filterRegistrationBean.filter == originVerifyingFilterMock
		filterRegistrationBean.urlPatterns[0] == '*'
		filterRegistrationBean.order == -1
	}

	void "DefaultMethodSecurityExpressionHandler is created with PermissionEvaluatorImpl"() {
		when:
		DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = securityConfiguration.defaultMethodSecurityExpressionHandler()

		then:
		0 * _
		defaultMethodSecurityExpressionHandler != null
		defaultMethodSecurityExpressionHandler.permissionEvaluator == permissionEvaluatorImplMock
	}

}
