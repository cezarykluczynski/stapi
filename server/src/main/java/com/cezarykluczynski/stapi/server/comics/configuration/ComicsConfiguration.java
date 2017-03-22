package com.cezarykluczynski.stapi.server.comics.configuration;

import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsRestEndpoint;
import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsSoapEndpoint;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullSoapMapper;
import com.cezarykluczynski.stapi.server.comics.reader.ComicsRestReader;
import com.cezarykluczynski.stapi.server.comics.reader.ComicsSoapReader;
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
public class ComicsConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint comicsSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new ComicsSoapEndpoint(applicationContext.getBean(ComicsSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/comics");
		return endpoint;
	}

	@Bean
	public ComicsRestEndpoint comicsRestEndpoint() {
		return new ComicsRestEndpoint(applicationContext.getBean(ComicsRestReader.class));
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
