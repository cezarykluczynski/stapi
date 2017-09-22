package com.cezarykluczynski.stapi.server.configuration

import com.cezarykluczynski.stapi.server.common.converter.LocalDateRestParamConverterProvider
import com.cezarykluczynski.stapi.server.common.throttle.rest.RestExceptionMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDExceptionMapper
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.web.servlet.view.JstlView
import org.springframework.web.servlet.view.UrlBasedViewResolver
import spock.lang.Specification

class CxfConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private CxfConfiguration cxfConfiguration

	void setup() {
		applicationContextMock = Mock()
		cxfConfiguration = new CxfConfiguration()
	}

	void "ServletRegistrationBean is created"() {
		when:
		ServletRegistrationBean servletRegistrationBean = cxfConfiguration.cxfServletRegistrationBean()

		then:
		servletRegistrationBean.servlet instanceof CXFServlet
		servletRegistrationBean.urlMappings.contains('/api/*')
	}

	void "creates ObjectMapper"() {
		when:
		ObjectMapper objectMapper = cxfConfiguration.objectMapper()

		then:
		objectMapper != null
	}

	void "creates CxfRestPrettyPrintContainerResponseFilter"() {
		when:
		CxfRestPrettyPrintContainerResponseFilter cxfRestPrettyPrintContainerResponseFilter = cxfConfiguration
				.cxfRestPrettyPrintContainerResponseFilter()

		then:
		cxfRestPrettyPrintContainerResponseFilter != null
	}

	void "creates CrossOriginResourceSharingFilter"() {
		when:
		CrossOriginResourceSharingFilter crossOriginResourceSharingFilter = cxfConfiguration.crossOriginResourceSharingFilter()

		then:
		crossOriginResourceSharingFilter != null
	}

	void "creates LocalDateRestParamConverterProvider"() {
		when:
		LocalDateRestParamConverterProvider localDateRestParamConverterProvider = cxfConfiguration.localDateRestParamConverterProvider()

		then:
		localDateRestParamConverterProvider != null
	}

	void "creates RestExceptionMapper"() {
		when:
		RestExceptionMapper restExceptionMapper = cxfConfiguration.restExceptionMapper()

		then:
		restExceptionMapper != null
	}

	void "creates MissingUIDExceptionMapper"() {
		when:
		MissingUIDExceptionMapper missingUIDExceptionMapper = cxfConfiguration.missingUIDExceptionMapper()

		then:
		missingUIDExceptionMapper != null
	}

	void "UrlBasedViewResolver is created"() {
		when:
		UrlBasedViewResolver urlBasedViewResolver = cxfConfiguration.urlBasedViewResolver()

		then:
		urlBasedViewResolver != null
		urlBasedViewResolver.viewClass == JstlView
		urlBasedViewResolver.prefix == '/'
		urlBasedViewResolver.suffix == '.html'
	}

}
