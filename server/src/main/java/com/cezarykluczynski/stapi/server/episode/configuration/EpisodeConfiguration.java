package com.cezarykluczynski.stapi.server.episode.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeRestEndpoint;
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeSoapEndpoint;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullRestMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class EpisodeConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint episodeEndpoint() {
		return endpointFactory.createSoapEndpoint(EpisodeSoapEndpoint.class, EpisodeSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server episodeServer() {
		return endpointFactory.createRestEndpoint(EpisodeRestEndpoint.class, EpisodeRestEndpoint.ADDRESS);
	}

	@Bean
	public EpisodeBaseSoapMapper episodeBaseSoapMapper() {
		return Mappers.getMapper(EpisodeBaseSoapMapper.class);
	}

	@Bean
	public EpisodeFullSoapMapper episodeFullSoapMapper() {
		return Mappers.getMapper(EpisodeFullSoapMapper.class);
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
