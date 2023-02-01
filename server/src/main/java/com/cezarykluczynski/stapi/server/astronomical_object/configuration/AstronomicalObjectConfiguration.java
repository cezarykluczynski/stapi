package com.cezarykluczynski.stapi.server.astronomical_object.configuration;

import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectRestEndpoint;
import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectV2RestEndpoint;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseRestMapper;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AstronomicalObjectConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server astronomicalObjectServer() {
		return endpointFactory.createRestEndpoint(AstronomicalObjectRestEndpoint.class, AstronomicalObjectRestEndpoint.ADDRESS);
	}

	@Bean
	public Server astronomicalObjectV2Server() {
		return endpointFactory.createRestEndpoint(AstronomicalObjectV2RestEndpoint.class, AstronomicalObjectV2RestEndpoint.ADDRESS);
	}

	@Bean
	public AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper() {
		return Mappers.getMapper(AstronomicalObjectBaseRestMapper.class);
	}

	@Bean
	public AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper() {
		return Mappers.getMapper(AstronomicalObjectFullRestMapper.class);
	}

}
