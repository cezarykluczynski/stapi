package com.cezarykluczynski.stapi.server.book_collection.configuration;

import com.cezarykluczynski.stapi.server.book_collection.endpoint.BookCollectionRestEndpoint;
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookCollectionConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server bookCollectionServer() {
		return endpointFactory.createRestEndpoint(BookCollectionRestEndpoint.class, BookCollectionRestEndpoint.ADDRESS);
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
