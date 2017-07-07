package com.cezarykluczynski.stapi.server.video_release.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.video_release.endpoint.VideoReleaseRestEndpoint;
import com.cezarykluczynski.stapi.server.video_release.endpoint.VideoReleaseSoapEndpoint;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseSoapMapper;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullRestMapper;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class VideoReleaseConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint videoReleaseEndpoint() {
		return endpointFactory.createSoapEndpoint(VideoReleaseSoapEndpoint.class, VideoReleaseSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server videoReleaseServer() {
		return endpointFactory.createRestEndpoint(VideoReleaseRestEndpoint.class, VideoReleaseRestEndpoint.ADDRESS);
	}

	@Bean
	public VideoReleaseBaseSoapMapper videoReleaseBaseSoapMapper() {
		return Mappers.getMapper(VideoReleaseBaseSoapMapper.class);
	}

	@Bean
	public VideoReleaseFullSoapMapper videoReleaseFullSoapMapper() {
		return Mappers.getMapper(VideoReleaseFullSoapMapper.class);
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
