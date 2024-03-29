package com.cezarykluczynski.stapi.server.common.endpoint;

import com.cezarykluczynski.stapi.server.common.converter.LocalDateRestParamConverterProvider;
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDExceptionMapper;
import com.cezarykluczynski.stapi.server.configuration.CxfRestPrettyPrintContainerResponseFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import com.google.common.collect.Lists;
import jakarta.inject.Inject;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class EndpointFactory {

	@Inject
	private ApplicationContext applicationContext;

	public <T> Server createRestEndpoint(Class<T> implementorClass, String address) {
		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		T implementor = applicationContext.getBean(implementorClass);
		factory.setBus(applicationContext.getBean(SpringBus.class));
		factory.setAddress(address);
		factory.setProviders(Lists.newArrayList(
				new JacksonJsonProvider(applicationContext.getBean(ObjectMapper.class)),
				applicationContext.getBean(CxfRestPrettyPrintContainerResponseFilter.class),
				applicationContext.getBean(LocalDateRestParamConverterProvider.class),
				applicationContext.getBean(CrossOriginResourceSharingFilter.class),
				applicationContext.getBean(MissingUIDExceptionMapper.class)));
		factory.setServiceBeans(Lists.newArrayList(implementor));
		return factory.create();
	}

}
