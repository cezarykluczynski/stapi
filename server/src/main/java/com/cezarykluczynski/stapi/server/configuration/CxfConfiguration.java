package com.cezarykluczynski.stapi.server.configuration;

import com.cezarykluczynski.stapi.server.common.converter.LocalDateRestParamConverterProvider;
import com.cezarykluczynski.stapi.server.common.throttle.rest.RestExceptionMapper;
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDExceptionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@ImportResource({
		"classpath:META-INF/cxf/cxf.xml",
		"classpath:META-INF/cxf/cxf-servlet.xml"
})
public class CxfConfiguration {

	public static final boolean CORS_ALLOW_ALL_ORIGINS = true;
	public static final int CORS_MAX_AGE = 3600;

	@Bean
	public ServletRegistrationBean cxfServletRegistrationBean() {
		return new ServletRegistrationBean(new CXFServlet(), "/api/*");
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return mapper;
	}

	@Bean
	public CxfRestPrettyPrintContainerResponseFilter cxfRestPrettyPrintContainerResponseFilter() {
		return new CxfRestPrettyPrintContainerResponseFilter();
	}

	@Bean
	public CrossOriginResourceSharingFilter crossOriginResourceSharingFilter() {
		return new CrossOriginResourceSharingFilter();
	}

	@Bean
	public LocalDateRestParamConverterProvider localDateRestParamConverterProvider() {
		return new LocalDateRestParamConverterProvider();
	}

	@Bean
	public RestExceptionMapper restExceptionMapper() {
		return new RestExceptionMapper();
	}

	@Bean
	public MissingUIDExceptionMapper missingUIDExceptionMapper() {
		return new MissingUIDExceptionMapper();
	}

	@Bean
	public UrlBasedViewResolver urlBasedViewResolver() {
		UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();

		urlBasedViewResolver.setViewClass(JstlView.class);
		urlBasedViewResolver.setPrefix("/");
		urlBasedViewResolver.setSuffix(".html");

		return urlBasedViewResolver;
	}

}
