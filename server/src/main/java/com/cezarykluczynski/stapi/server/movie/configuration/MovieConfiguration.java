package com.cezarykluczynski.stapi.server.movie.configuration;

import com.cezarykluczynski.stapi.server.movie.endpoint.MovieRestEndpoint;
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieSoapEndpoint;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullSoapMapper;
import com.cezarykluczynski.stapi.server.movie.reader.MovieRestReader;
import com.cezarykluczynski.stapi.server.movie.reader.MovieSoapReader;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class MovieConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint movieSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new MovieSoapEndpoint(applicationContext.getBean(MovieSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/movie");
		return endpoint;
	}

	@Bean
	public MovieRestEndpoint movieRestEndpoint() {
		return new MovieRestEndpoint(applicationContext.getBean(MovieRestReader.class));
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
