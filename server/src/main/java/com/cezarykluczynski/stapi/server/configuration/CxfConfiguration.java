package com.cezarykluczynski.stapi.server.configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.collect.Lists;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Configuration
@ImportResource({
		"classpath:META-INF/cxf/cxf.xml",
		"classpath:META-INF/cxf/cxf-servlet.xml"
})
public class CxfConfiguration extends SpringBootServletInitializer {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public ServletRegistrationBean cxfServletRegistrationBean() {
		return new ServletRegistrationBean(new CXFServlet(), "/api/*");
	}

	@Bean
	public Server cxfServer() {
		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setBus(applicationContext.getBean(SpringBus.class));
		factory.setProviders(Lists.newArrayList(new JacksonJsonProvider()));
		factory.setServiceBeans(Lists.newArrayList(applicationContext.getBeansWithAnnotation(Path.class).values()));
		return factory.create();
	}

}
