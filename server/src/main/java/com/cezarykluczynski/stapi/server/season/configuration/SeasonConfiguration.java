package com.cezarykluczynski.stapi.server.season.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.season.endpoint.SeasonRestEndpoint;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseRestMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeasonConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server seasonServer() {
		return endpointFactory.createRestEndpoint(SeasonRestEndpoint.class, SeasonRestEndpoint.ADDRESS);
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
