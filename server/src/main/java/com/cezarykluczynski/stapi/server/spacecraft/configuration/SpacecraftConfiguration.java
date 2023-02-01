package com.cezarykluczynski.stapi.server.spacecraft.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.spacecraft.endpoint.SpacecraftRestEndpoint;
import com.cezarykluczynski.stapi.server.spacecraft.endpoint.SpacecraftV2RestEndpoint;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpacecraftConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server spacecraftServer() {
		return endpointFactory.createRestEndpoint(SpacecraftRestEndpoint.class, SpacecraftRestEndpoint.ADDRESS);
	}

	@Bean
	public Server spacecraftV2Server() {
		return endpointFactory.createRestEndpoint(SpacecraftV2RestEndpoint.class, SpacecraftV2RestEndpoint.ADDRESS);
	}

	@Bean
	public SpacecraftBaseRestMapper spacecraftBaseRestMapper() {
		return Mappers.getMapper(SpacecraftBaseRestMapper.class);
	}

	@Bean
	public SpacecraftFullRestMapper spacecraftFullRestMapper() {
		return Mappers.getMapper(SpacecraftFullRestMapper.class);
	}

}
