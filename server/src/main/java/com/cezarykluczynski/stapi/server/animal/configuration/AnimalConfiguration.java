package com.cezarykluczynski.stapi.server.animal.configuration;

import com.cezarykluczynski.stapi.server.animal.endpoint.AnimalRestEndpoint;
import com.cezarykluczynski.stapi.server.animal.endpoint.AnimalSoapEndpoint;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseRestMapper;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseSoapMapper;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullRestMapper;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class AnimalConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint animalEndpoint() {
		return endpointFactory.createSoapEndpoint(AnimalSoapEndpoint.class, AnimalSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server animalServer() {
		return endpointFactory.createRestEndpoint(AnimalRestEndpoint.class, AnimalRestEndpoint.ADDRESS);
	}

	@Bean
	public AnimalBaseSoapMapper animalBaseSoapMapper() {
		return Mappers.getMapper(AnimalBaseSoapMapper.class);
	}

	@Bean
	public AnimalFullSoapMapper animalFullSoapMapper() {
		return Mappers.getMapper(AnimalFullSoapMapper.class);
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
