package com.cezarykluczynski.stapi.server.movie.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieRestEndpoint;
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieSoapEndpoint;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class MovieConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint movieEndpoint() {
		return endpointFactory.createSoapEndpoint(MovieSoapEndpoint.class, MovieSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server movieServer() {
		return endpointFactory.createRestEndpoint(MovieRestEndpoint.class, MovieRestEndpoint.ADDRESS);
	}

	@Bean
	public MovieBaseSoapMapper movieBaseSoapMapper() {
		return Mappers.getMapper(MovieBaseSoapMapper.class);
	}

	@Bean
	public MovieFullSoapMapper movieFullSoapMapper() {
		return Mappers.getMapper(MovieFullSoapMapper.class);
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
