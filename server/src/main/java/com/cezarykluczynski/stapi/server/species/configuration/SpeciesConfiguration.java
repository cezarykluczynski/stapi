package com.cezarykluczynski.stapi.server.species.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesRestEndpoint;
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesV2RestEndpoint;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpeciesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server speciesServer() {
		return endpointFactory.createRestEndpoint(SpeciesRestEndpoint.class, SpeciesRestEndpoint.ADDRESS);
	}

	@Bean
	public Server speciesV2Server() {
		return endpointFactory.createRestEndpoint(SpeciesV2RestEndpoint.class, SpeciesV2RestEndpoint.ADDRESS);
	}

	@Bean
	public SpeciesBaseRestMapper speciesBaseRestMapper() {
		return Mappers.getMapper(SpeciesBaseRestMapper.class);
	}

	@Bean
	public SpeciesFullRestMapper speciesFullRestMapper() {
		return Mappers.getMapper(SpeciesFullRestMapper.class);
	}

}
