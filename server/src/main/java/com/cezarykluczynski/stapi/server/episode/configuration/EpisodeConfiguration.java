package com.cezarykluczynski.stapi.server.episode.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeRestEndpoint;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EpisodeConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server episodeServer() {
		return endpointFactory.createRestEndpoint(EpisodeRestEndpoint.class, EpisodeRestEndpoint.ADDRESS);
	}

	@Bean
	public EpisodeBaseRestMapper episodeBaseRestMapper() {
		return Mappers.getMapper(EpisodeBaseRestMapper.class);
	}

	@Bean
	public EpisodeFullRestMapper episodeFullRestMapper() {
		return Mappers.getMapper(EpisodeFullRestMapper.class);
	}

}
