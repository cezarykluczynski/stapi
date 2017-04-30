package com.cezarykluczynski.stapi.server.book.configuration;

import com.cezarykluczynski.stapi.server.book.endpoint.BookRestEndpoint;
import com.cezarykluczynski.stapi.server.book.endpoint.BookSoapEndpoint;
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper;
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseSoapMapper;
import com.cezarykluczynski.stapi.server.book.mapper.BookFullRestMapper;
import com.cezarykluczynski.stapi.server.book.mapper.BookFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class BookConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint bookEndpoint() {
		return endpointFactory.createSoapEndpoint(BookSoapEndpoint.class, BookSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server bookServer() {
		return endpointFactory.createRestEndpoint(BookRestEndpoint.class, BookRestEndpoint.ADDRESS);
	}

	@Bean
	public BookBaseSoapMapper bookBaseSoapMapper() {
		return Mappers.getMapper(BookBaseSoapMapper.class);
	}

	@Bean
	public BookFullSoapMapper bookFullSoapMapper() {
		return Mappers.getMapper(BookFullSoapMapper.class);
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
