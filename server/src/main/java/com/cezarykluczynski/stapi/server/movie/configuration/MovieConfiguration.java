package com.cezarykluczynski.stapi.server.movie.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieRestEndpoint;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server movieServer() {
		return endpointFactory.createRestEndpoint(MovieRestEndpoint.class, MovieRestEndpoint.ADDRESS);
	}

	@Bean
	public MovieBaseRestMapper movieBaseRestMapper() {
		return Mappers.getMapper(MovieBaseRestMapper.class);
	}

	@Bean
	public MovieFullRestMapper movieFullRestMapper() {
		return Mappers.getMapper(MovieFullRestMapper.class);
	}

}
