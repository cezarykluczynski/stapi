package com.cezarykluczynski.stapi.server.animal.configuration;

import com.cezarykluczynski.stapi.server.animal.endpoint.AnimalRestEndpoint;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseRestMapper;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server animalServer() {
		return endpointFactory.createRestEndpoint(AnimalRestEndpoint.class, AnimalRestEndpoint.ADDRESS);
	}

	@Bean
	public AnimalBaseRestMapper animalBaseRestMapper() {
		return Mappers.getMapper(AnimalBaseRestMapper.class);
	}

	@Bean
	public AnimalFullRestMapper animalFullRestMapper() {
		return Mappers.getMapper(AnimalFullRestMapper.class);
	}

}
