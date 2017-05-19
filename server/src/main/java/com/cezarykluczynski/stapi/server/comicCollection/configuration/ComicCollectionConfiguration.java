package com.cezarykluczynski.stapi.server.comicCollection.configuration;

import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionRestEndpoint;
import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionSoapEndpoint;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullRestMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class ComicCollectionConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint comicCollectionEndpoint() {
		return endpointFactory.createSoapEndpoint(ComicCollectionSoapEndpoint.class, ComicCollectionSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server comicCollectionServer() {
		return endpointFactory.createRestEndpoint(ComicCollectionRestEndpoint.class, ComicCollectionRestEndpoint.ADDRESS);
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
