package com.cezarykluczynski.stapi.server.technology.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.technology.endpoint.TechnologyRestEndpoint;
import com.cezarykluczynski.stapi.server.technology.endpoint.TechnologyV2RestEndpoint;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TechnologyConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server technologyServer() {
		return endpointFactory.createRestEndpoint(TechnologyRestEndpoint.class, TechnologyRestEndpoint.ADDRESS);
	}

	@Bean
	public Server technologyV2Server() {
		return endpointFactory.createRestEndpoint(TechnologyV2RestEndpoint.class, TechnologyV2RestEndpoint.ADDRESS);
	}

	@Bean
	public TechnologyBaseRestMapper technologyBaseRestMapper() {
		return Mappers.getMapper(TechnologyBaseRestMapper.class);
	}

	@Bean
	public TechnologyFullRestMapper technologyFullRestMapper() {
		return Mappers.getMapper(TechnologyFullRestMapper.class);
	}

}
