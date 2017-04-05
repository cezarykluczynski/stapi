package com.cezarykluczynski.stapi.server.common.endpoint;

import com.cezarykluczynski.stapi.server.configuration.interceptor.ApiThrottlingInterceptor;
import com.google.common.collect.Lists;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.message.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;
import java.util.List;

@Service
public class EndpointFactory {

	@Inject
	private ApplicationContext applicationContext;

	public Endpoint createSoapEndpoint(Class<?> implementorClass, String address) {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = applicationContext.getBean(implementorClass);
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		List<Interceptor<? extends Message>> interceptorList = Lists.newArrayList(applicationContext.getBean(ApiThrottlingInterceptor.class));
		endpoint.setInInterceptors(interceptorList);
		endpoint.publish(address);
		return endpoint;
	}

}
