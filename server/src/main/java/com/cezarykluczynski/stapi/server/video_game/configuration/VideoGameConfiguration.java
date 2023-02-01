package com.cezarykluczynski.stapi.server.video_game.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.video_game.endpoint.VideoGameRestEndpoint;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseRestMapper;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VideoGameConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server videoGameServer() {
		return endpointFactory.createRestEndpoint(VideoGameRestEndpoint.class, VideoGameRestEndpoint.ADDRESS);
	}

	@Bean
	public VideoGameBaseRestMapper videoGameBaseRestMapper() {
		return Mappers.getMapper(VideoGameBaseRestMapper.class);
	}

	@Bean
	public VideoGameFullRestMapper videoGameFullRestMapper() {
		return Mappers.getMapper(VideoGameFullRestMapper.class);
	}

}
