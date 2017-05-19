package com.cezarykluczynski.stapi.server.species.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesRestEndpoint;
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesSoapEndpoint;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class SpeciesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint speciesEndpoint() {
		return endpointFactory.createSoapEndpoint(SpeciesSoapEndpoint.class, SpeciesSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server speciesServer() {
		return endpointFactory.createRestEndpoint(SpeciesRestEndpoint.class, SpeciesRestEndpoint.ADDRESS);
	}

	@Bean
	public SpeciesBaseSoapMapper speciesBaseSoapMapper() {
		return Mappers.getMapper(SpeciesBaseSoapMapper.class);
	}

	@Bean
	public SpeciesFullSoapMapper speciesFullSoapMapper() {
		return Mappers.getMapper(SpeciesFullSoapMapper.class);
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
