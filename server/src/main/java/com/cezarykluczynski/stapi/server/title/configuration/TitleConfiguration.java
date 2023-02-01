package com.cezarykluczynski.stapi.server.title.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.title.endpoint.TitleRestEndpoint;
import com.cezarykluczynski.stapi.server.title.endpoint.TitleV2RestEndpoint;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TitleConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server titleServer() {
		return endpointFactory.createRestEndpoint(TitleRestEndpoint.class, TitleRestEndpoint.ADDRESS);
	}

	@Bean
	public Server titleV2Server() {
		return endpointFactory.createRestEndpoint(TitleV2RestEndpoint.class, TitleV2RestEndpoint.ADDRESS);
	}

	@Bean
	public TitleBaseRestMapper titleBaseRestMapper() {
		return Mappers.getMapper(TitleBaseRestMapper.class);
	}

	@Bean
	public TitleFullRestMapper titleFullRestMapper() {
		return Mappers.getMapper(TitleFullRestMapper.class);
	}

}
