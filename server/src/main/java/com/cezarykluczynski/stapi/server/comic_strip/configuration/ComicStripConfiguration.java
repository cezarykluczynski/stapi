package com.cezarykluczynski.stapi.server.comic_strip.configuration;

import com.cezarykluczynski.stapi.server.comic_strip.endpoint.ComicStripRestEndpoint;
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComicStripConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server comicStripServer() {
		return endpointFactory.createRestEndpoint(ComicStripRestEndpoint.class, ComicStripRestEndpoint.ADDRESS);
	}

	@Bean
	public ComicStripBaseRestMapper comicStripBaseRestMapper() {
		return Mappers.getMapper(ComicStripBaseRestMapper.class);
	}

	@Bean
	public ComicStripFullRestMapper comicStripFullRestMapper() {
		return Mappers.getMapper(ComicStripFullRestMapper.class);
	}

}
