package com.cezarykluczynski.stapi.server.comics.configuration;

import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsRestEndpoint;
import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsSoapEndpoint;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class ComicsConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint comicsEndpoint() {
		return endpointFactory.createSoapEndpoint(ComicsSoapEndpoint.class, ComicsSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server comicsServer() {
		return endpointFactory.createRestEndpoint(ComicsRestEndpoint.class, ComicsRestEndpoint.ADDRESS);
	}

	@Bean
	public ComicsBaseSoapMapper comicsBaseSoapMapper() {
		return Mappers.getMapper(ComicsBaseSoapMapper.class);
	}

	@Bean
	public ComicsFullSoapMapper comicsFullSoapMapper() {
		return Mappers.getMapper(ComicsFullSoapMapper.class);
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
