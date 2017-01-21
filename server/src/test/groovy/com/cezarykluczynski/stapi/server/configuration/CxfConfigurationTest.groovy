package com.cezarykluczynski.stapi.server.configuration

import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint
import com.google.common.collect.Maps
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.endpoint.Server
import org.apache.cxf.endpoint.ServerImpl
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.ws.rs.Path

class CxfConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private CxfConfiguration cxfConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		cxfConfiguration = new CxfConfiguration()
	}

	void "ServletRegistrationBean is created"() {
		when:
		ServletRegistrationBean servletRegistrationBean = cxfConfiguration.cxfServletRegistrationBean()

		then:
		servletRegistrationBean.servlet instanceof CXFServlet
		servletRegistrationBean.urlMappings.contains('/api/*')
	}

	@SuppressWarnings(['EmptyCatchBlock', 'CatchException'])
	void "CxfServer is created"() {
		given:
		Map<String, Object> serviceBeans = Maps.newHashMap()
		serviceBeans.put('SeriesRestEndpoint', new SeriesRestEndpoint(null))
		cxfConfiguration.applicationContext = applicationContextMock

		when:
		Server server = cxfConfiguration.cxfServer()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> new SpringBus()
		1 * applicationContextMock.getBeansWithAnnotation(Path) >> serviceBeans
		server instanceof ServerImpl
		server.started

		cleanup:
		try {
			server.destroy()
		} catch (Exception e) {
		}
	}

}
