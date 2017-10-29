package com.cezarykluczynski.stapi.server.common.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.CommonRestEndpoint;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration("serverCommonConfiguration")
public class CommonConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server commonServer() {
		return endpointFactory.createRestEndpoint(CommonRestEndpoint.class, CommonRestEndpoint.ADDRESS);
	}

}
