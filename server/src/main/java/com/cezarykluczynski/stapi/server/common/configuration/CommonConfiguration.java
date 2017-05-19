package com.cezarykluczynski.stapi.server.common.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.CommonRestEndpoint;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class CommonConfiguration {

	@Inject
	private EndpointFactory endpointFactory;


	@Bean
	public PageMapper pageMapper() {
		return Mappers.getMapper(PageMapper.class);
	}

	@Bean
	public Server commonServer() {
		return endpointFactory.createRestEndpoint(CommonRestEndpoint.class, CommonRestEndpoint.ADDRESS);
	}

}
