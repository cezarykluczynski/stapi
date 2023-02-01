package com.cezarykluczynski.stapi.server.book.configuration;

import com.cezarykluczynski.stapi.server.book.endpoint.BookRestEndpoint;
import com.cezarykluczynski.stapi.server.book.endpoint.BookV2RestEndpoint;
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper;
import com.cezarykluczynski.stapi.server.book.mapper.BookFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server bookServer() {
		return endpointFactory.createRestEndpoint(BookRestEndpoint.class, BookRestEndpoint.ADDRESS);
	}

	@Bean
	public Server bookV2Server() {
		return endpointFactory.createRestEndpoint(BookV2RestEndpoint.class, BookV2RestEndpoint.ADDRESS);
	}

	@Bean
	public BookBaseRestMapper bookBaseRestMapper() {
		return Mappers.getMapper(BookBaseRestMapper.class);
	}

	@Bean
	public BookFullRestMapper bookFullRestMapper() {
		return Mappers.getMapper(BookFullRestMapper.class);
	}

}
