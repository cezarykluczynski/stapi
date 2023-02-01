package com.cezarykluczynski.stapi.server.soundtrack.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.soundtrack.endpoint.SoundtrackRestEndpoint;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseRestMapper;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoundtrackConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server soundtrackServer() {
		return endpointFactory.createRestEndpoint(SoundtrackRestEndpoint.class, SoundtrackRestEndpoint.ADDRESS);
	}

	@Bean
	public SoundtrackBaseRestMapper soundtrackBaseRestMapper() {
		return Mappers.getMapper(SoundtrackBaseRestMapper.class);
	}

	@Bean
	public SoundtrackFullRestMapper soundtrackFullRestMapper() {
		return Mappers.getMapper(SoundtrackFullRestMapper.class);
	}

}
