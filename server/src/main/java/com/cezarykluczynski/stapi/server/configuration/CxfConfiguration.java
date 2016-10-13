package com.cezarykluczynski.stapi.server.configuration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
		"classpath:META-INF/cxf/cxf.xml",
		"classpath:META-INF/cxf/cxf-servlet.xml"
})
public class CxfConfiguration extends SpringBootServletInitializer {

	@Bean
	public ServletRegistrationBean cxfServletRegistrationBean() {
		return new ServletRegistrationBean(new CXFServlet(), "/api/*");
	}

}
