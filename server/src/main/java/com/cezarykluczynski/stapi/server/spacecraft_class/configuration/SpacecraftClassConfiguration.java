package com.cezarykluczynski.stapi.server.spacecraft_class.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.spacecraft_class.endpoint.SpacecraftClassRestEndpoint;
import com.cezarykluczynski.stapi.server.spacecraft_class.endpoint.SpacecraftClassV2RestEndpoint;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpacecraftClassConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server spacecraftClassServer() {
		return endpointFactory.createRestEndpoint(SpacecraftClassRestEndpoint.class, SpacecraftClassRestEndpoint.ADDRESS);
	}

	@Bean
	public Server spacecraftClassV2Server() {
		return endpointFactory.createRestEndpoint(SpacecraftClassV2RestEndpoint.class, SpacecraftClassV2RestEndpoint.ADDRESS);
	}

	@Bean
	public SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper() {
		return Mappers.getMapper(SpacecraftClassBaseRestMapper.class);
	}

	@Bean
	public SpacecraftClassFullRestMapper spacecraftClassFullRestMapper() {
		return Mappers.getMapper(SpacecraftClassFullRestMapper.class);
	}

}
