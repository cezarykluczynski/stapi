package com.cezarykluczynski.stapi.server.performer.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerRestEndpoint;
import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerSoapEndpoint;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullRestMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class PerformerConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint performerEndpoint() {
		return endpointFactory.createSoapEndpoint(PerformerSoapEndpoint.class, PerformerSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server performerServer() {
		return endpointFactory.createRestEndpoint(PerformerRestEndpoint.class, PerformerRestEndpoint.ADDRESS);
	}

	@Bean
	public PerformerBaseSoapMapper performerBaseSoapMapper() {
		return Mappers.getMapper(PerformerBaseSoapMapper.class);
	}

	@Bean
	public PerformerFullSoapMapper performerFullSoapMapper() {
		return Mappers.getMapper(PerformerFullSoapMapper.class);
	}

	@Bean
	public PerformerBaseRestMapper performerBaseRestMapper() {
		return Mappers.getMapper(PerformerBaseRestMapper.class);
	}

	@Bean
	public PerformerFullRestMapper performerFullRestMapper() {
		return Mappers.getMapper(PerformerFullRestMapper.class);
	}

}
