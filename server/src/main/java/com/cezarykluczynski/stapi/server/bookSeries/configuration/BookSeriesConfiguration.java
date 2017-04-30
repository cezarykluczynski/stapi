package com.cezarykluczynski.stapi.server.bookSeries.configuration;

import com.cezarykluczynski.stapi.server.bookSeries.endpoint.BookSeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.bookSeries.endpoint.BookSeriesSoapEndpoint;
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class BookSeriesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint bookSeriesEndpoint() {
		return endpointFactory.createSoapEndpoint(BookSeriesSoapEndpoint.class, BookSeriesSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server bookSeriesServer() {
		return endpointFactory.createRestEndpoint(BookSeriesRestEndpoint.class, BookSeriesRestEndpoint.ADDRESS);
	}

	@Bean
	public BookSeriesBaseSoapMapper bookSeriesBaseSoapMapper() {
		return Mappers.getMapper(BookSeriesBaseSoapMapper.class);
	}

	@Bean
	public BookSeriesFullSoapMapper bookSeriesFullSoapMapper() {
		return Mappers.getMapper(BookSeriesFullSoapMapper.class);
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
