package com.cezarykluczynski.stapi.server.book_series.configuration;

import com.cezarykluczynski.stapi.server.book_series.endpoint.BookSeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookSeriesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server bookSeriesServer() {
		return endpointFactory.createRestEndpoint(BookSeriesRestEndpoint.class, BookSeriesRestEndpoint.ADDRESS);
	}

	@Bean
	public BookSeriesBaseRestMapper bookSeriesBaseRestMapper() {
		return Mappers.getMapper(BookSeriesBaseRestMapper.class);
	}

	@Bean
	public BookSeriesFullRestMapper bookSeriesFullRestMapper() {
		return Mappers.getMapper(BookSeriesFullRestMapper.class);
	}

}
