package com.cezarykluczynski.stapi.server.comic_series.configuration;

import com.cezarykluczynski.stapi.server.comic_series.endpoint.ComicSeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.comic_series.endpoint.ComicSeriesSoapEndpoint;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class ComicSeriesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint comicSeriesEndpoint() {
		return endpointFactory.createSoapEndpoint(ComicSeriesSoapEndpoint.class, ComicSeriesSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server comicSeriesServer() {
		return endpointFactory.createRestEndpoint(ComicSeriesRestEndpoint.class, ComicSeriesRestEndpoint.ADDRESS);
	}

	@Bean
	public ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapper() {
		return Mappers.getMapper(ComicSeriesBaseSoapMapper.class);
	}

	@Bean
	public ComicSeriesFullSoapMapper comicSeriesFullSoapMapper() {
		return Mappers.getMapper(ComicSeriesFullSoapMapper.class);
	}

	@Bean
	public ComicSeriesBaseRestMapper comicSeriesBaseRestMapper() {
		return Mappers.getMapper(ComicSeriesBaseRestMapper.class);
	}

	@Bean
	public ComicSeriesFullRestMapper comicSeriesFullRestMapper() {
		return Mappers.getMapper(ComicSeriesFullRestMapper.class);
	}

}
