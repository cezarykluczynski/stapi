package com.cezarykluczynski.stapi.server.element.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.element.endpoint.ElementRestEndpoint;
import com.cezarykluczynski.stapi.server.element.endpoint.ElementV2RestEndpoint;
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper;
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElementConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server elementServer() {
		return endpointFactory.createRestEndpoint(ElementRestEndpoint.class, ElementRestEndpoint.ADDRESS);
	}

	@Bean
	public Server elementV2Server() {
		return endpointFactory.createRestEndpoint(ElementV2RestEndpoint.class, ElementV2RestEndpoint.ADDRESS);
	}

	@Bean
	public ElementBaseRestMapper elementBaseRestMapper() {
		return Mappers.getMapper(ElementBaseRestMapper.class);
	}

	@Bean
	public ElementFullRestMapper elementFullRestMapper() {
		return Mappers.getMapper(ElementFullRestMapper.class);
	}

}
