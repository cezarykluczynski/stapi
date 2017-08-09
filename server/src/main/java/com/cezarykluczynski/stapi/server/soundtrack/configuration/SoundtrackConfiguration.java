package com.cezarykluczynski.stapi.server.soundtrack.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.soundtrack.endpoint.SoundtrackRestEndpoint;
import com.cezarykluczynski.stapi.server.soundtrack.endpoint.SoundtrackSoapEndpoint;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseRestMapper;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseSoapMapper;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullRestMapper;
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class SoundtrackConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint soundtrackEndpoint() {
		return endpointFactory.createSoapEndpoint(SoundtrackSoapEndpoint.class, SoundtrackSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server soundtrackServer() {
		return endpointFactory.createRestEndpoint(SoundtrackRestEndpoint.class, SoundtrackRestEndpoint.ADDRESS);
	}

	@Bean
	public SoundtrackBaseSoapMapper soundtrackBaseSoapMapper() {
		return Mappers.getMapper(SoundtrackBaseSoapMapper.class);
	}

	@Bean
	public SoundtrackFullSoapMapper soundtrackFullSoapMapper() {
		return Mappers.getMapper(SoundtrackFullSoapMapper.class);
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
