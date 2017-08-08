package com.cezarykluczynski.stapi.server.video_game.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.video_game.endpoint.VideoGameRestEndpoint;
import com.cezarykluczynski.stapi.server.video_game.endpoint.VideoGameSoapEndpoint;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseRestMapper;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseSoapMapper;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullRestMapper;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class VideoGameConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint videoGameEndpoint() {
		return endpointFactory.createSoapEndpoint(VideoGameSoapEndpoint.class, VideoGameSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server videoGameServer() {
		return endpointFactory.createRestEndpoint(VideoGameRestEndpoint.class, VideoGameRestEndpoint.ADDRESS);
	}

	@Bean
	public VideoGameBaseSoapMapper videoGameBaseSoapMapper() {
		return Mappers.getMapper(VideoGameBaseSoapMapper.class);
	}

	@Bean
	public VideoGameFullSoapMapper videoGameFullSoapMapper() {
		return Mappers.getMapper(VideoGameFullSoapMapper.class);
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
