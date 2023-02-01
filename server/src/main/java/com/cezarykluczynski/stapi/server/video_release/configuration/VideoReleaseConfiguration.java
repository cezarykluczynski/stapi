package com.cezarykluczynski.stapi.server.video_release.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.video_release.endpoint.VideoReleaseRestEndpoint;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VideoReleaseConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server videoReleaseServer() {
		return endpointFactory.createRestEndpoint(VideoReleaseRestEndpoint.class, VideoReleaseRestEndpoint.ADDRESS);
	}

	@Bean
	public VideoReleaseBaseRestMapper videoReleaseBaseRestMapper() {
		return Mappers.getMapper(VideoReleaseBaseRestMapper.class);
	}

	@Bean
	public VideoReleaseFullRestMapper videoReleaseFullRestMapper() {
		return Mappers.getMapper(VideoReleaseFullRestMapper.class);
	}

}
