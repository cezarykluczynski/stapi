package com.cezarykluczynski.stapi.server.comics.configuration;

import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsRestEndpoint;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComicsConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server comicsServer() {
		return endpointFactory.createRestEndpoint(ComicsRestEndpoint.class, ComicsRestEndpoint.ADDRESS);
	}

	@Bean
	public ComicsBaseRestMapper comicsBaseRestMapper() {
		return Mappers.getMapper(ComicsBaseRestMapper.class);
	}

	@Bean
	public ComicsFullRestMapper comicsFullRestMapper() {
		return Mappers.getMapper(ComicsFullRestMapper.class);
	}

}
