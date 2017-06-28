package com.cezarykluczynski.stapi.server.season.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.season.endpoint.SeasonRestEndpoint;
import com.cezarykluczynski.stapi.server.season.endpoint.SeasonSoapEndpoint;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseRestMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseSoapMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullRestMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class SeasonConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint seasonEndpoint() {
		return endpointFactory.createSoapEndpoint(SeasonSoapEndpoint.class, SeasonSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server seasonServer() {
		return endpointFactory.createRestEndpoint(SeasonRestEndpoint.class, SeasonRestEndpoint.ADDRESS);
	}

	@Bean
	public SeasonBaseSoapMapper seasonBaseSoapMapper() {
		return Mappers.getMapper(SeasonBaseSoapMapper.class);
	}

	@Bean
	public SeasonFullSoapMapper seasonFullSoapMapper() {
		return Mappers.getMapper(SeasonFullSoapMapper.class);
	}

	@Bean
	public SeasonBaseRestMapper seasonBaseRestMapper() {
		return Mappers.getMapper(SeasonBaseRestMapper.class);
	}

	@Bean
	public SeasonFullRestMapper seasonFullRestMapper() {
		return Mappers.getMapper(SeasonFullRestMapper.class);
	}

}
