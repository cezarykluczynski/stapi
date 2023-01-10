package com.cezarykluczynski.stapi.server.species.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesRestEndpoint;
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesSoapEndpoint;
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesV2RestEndpoint;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullSoapMapper;
import jakarta.inject.Inject;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	public Server speciesV2Server() {
		return endpointFactory.createRestEndpoint(SpeciesV2RestEndpoint.class, SpeciesV2RestEndpoint.ADDRESS);
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
