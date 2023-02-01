package com.cezarykluczynski.stapi.server.performer.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerRestEndpoint;
import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerV2RestEndpoint;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PerformerConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server performerServer() {
		return endpointFactory.createRestEndpoint(PerformerRestEndpoint.class, PerformerRestEndpoint.ADDRESS);
	}

	@Bean
	public Server performerV2Server() {
		return endpointFactory.createRestEndpoint(PerformerV2RestEndpoint.class, PerformerV2RestEndpoint.ADDRESS);
	}

	@Bean
	public PerformerBaseRestMapper performerBaseRestMapper() {
		return Mappers.getMapper(PerformerBaseRestMapper.class);
	}

	@Bean
	public PerformerFullRestMapper performerFullRestMapper() {
		return Mappers.getMapper(PerformerFullRestMapper.class);
	}

}
