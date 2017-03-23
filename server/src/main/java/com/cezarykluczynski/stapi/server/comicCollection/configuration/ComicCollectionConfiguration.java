package com.cezarykluczynski.stapi.server.comicCollection.configuration;

import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionRestEndpoint;
import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionSoapEndpoint;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullRestMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullSoapMapper;
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionRestReader;
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionSoapReader;
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
public class ComicCollectionConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint comicCollectionSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new ComicCollectionSoapEndpoint(applicationContext.getBean(ComicCollectionSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/comicCollection");
		return endpoint;
	}

	@Bean
	public ComicCollectionRestEndpoint comicCollectionRestEndpoint() {
		return new ComicCollectionRestEndpoint(applicationContext.getBean(ComicCollectionRestReader.class));
	}

	@Bean
	public ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapper() {
		return Mappers.getMapper(ComicCollectionBaseSoapMapper.class);
	}

	@Bean
	public ComicCollectionFullSoapMapper comicCollectionFullSoapMapper() {
		return Mappers.getMapper(ComicCollectionFullSoapMapper.class);
	}

	@Bean
	public ComicCollectionBaseRestMapper comicCollectionBaseRestMapper() {
		return Mappers.getMapper(ComicCollectionBaseRestMapper.class);
	}

	@Bean
	public ComicCollectionFullRestMapper comicCollectionFullRestMapper() {
		return Mappers.getMapper(ComicCollectionFullRestMapper.class);
	}

}
