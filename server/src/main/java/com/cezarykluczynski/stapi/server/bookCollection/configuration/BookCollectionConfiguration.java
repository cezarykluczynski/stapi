package com.cezarykluczynski.stapi.server.bookCollection.configuration;

import com.cezarykluczynski.stapi.server.bookCollection.endpoint.BookCollectionRestEndpoint;
import com.cezarykluczynski.stapi.server.bookCollection.endpoint.BookCollectionSoapEndpoint;
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionFullRestMapper;
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class BookCollectionConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint bookCollectionEndpoint() {
		return endpointFactory.createSoapEndpoint(BookCollectionSoapEndpoint.class, BookCollectionSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server bookCollectionServer() {
		return endpointFactory.createRestEndpoint(BookCollectionRestEndpoint.class, BookCollectionRestEndpoint.ADDRESS);
	}

	@Bean
	public BookCollectionBaseSoapMapper bookCollectionBaseSoapMapper() {
		return Mappers.getMapper(BookCollectionBaseSoapMapper.class);
	}

	@Bean
	public BookCollectionFullSoapMapper bookCollectionFullSoapMapper() {
		return Mappers.getMapper(BookCollectionFullSoapMapper.class);
	}

	@Bean
	public BookCollectionBaseRestMapper bookCollectionBaseRestMapper() {
		return Mappers.getMapper(BookCollectionBaseRestMapper.class);
	}

	@Bean
	public BookCollectionFullRestMapper bookCollectionFullRestMapper() {
		return Mappers.getMapper(BookCollectionFullRestMapper.class);
	}

}
