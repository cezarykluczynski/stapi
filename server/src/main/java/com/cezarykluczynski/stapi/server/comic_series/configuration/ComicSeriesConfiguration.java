package com.cezarykluczynski.stapi.server.comic_series.configuration;

import com.cezarykluczynski.stapi.server.comic_series.endpoint.ComicSeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComicSeriesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server comicSeriesServer() {
		return endpointFactory.createRestEndpoint(ComicSeriesRestEndpoint.class, ComicSeriesRestEndpoint.ADDRESS);
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
