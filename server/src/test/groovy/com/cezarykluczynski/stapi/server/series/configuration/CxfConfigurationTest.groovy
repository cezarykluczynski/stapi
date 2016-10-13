package com.cezarykluczynski.stapi.server.series.configuration

import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import spock.lang.Specification

class CxfConfigurationTest extends Specification {

	private CxfConfiguration cxfConfiguration

	def setup() {
		cxfConfiguration = new CxfConfiguration()
	}

	def "ServletRegistrationBean is created"() {
		when:
		ServletRegistrationBean servletRegistrationBean = cxfConfiguration.cxfServletRegistrationBean()

		then:
		servletRegistrationBean.servlet instanceof CXFServlet
		servletRegistrationBean.urlMappings.contains("/api/*")
	}

}
