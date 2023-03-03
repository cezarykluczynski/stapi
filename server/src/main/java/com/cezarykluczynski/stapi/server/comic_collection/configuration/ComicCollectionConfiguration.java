package com.cezarykluczynski.stapi.server.comic_collection.configuration;

import com.cezarykluczynski.stapi.server.comic_collection.endpoint.ComicCollectionRestEndpoint;
import com.cezarykluczynski.stapi.server.comic_collection.endpoint.ComicCollectionV2RestEndpoint;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComicCollectionConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server comicCollectionServer() {
		return endpointFactory.createRestEndpoint(ComicCollectionRestEndpoint.class, ComicCollectionRestEndpoint.ADDRESS);
	}

	@Bean
	public Server comicCollectionV2Server() {
		return endpointFactory.createRestEndpoint(ComicCollectionV2RestEndpoint.class, ComicCollectionV2RestEndpoint.ADDRESS);
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
