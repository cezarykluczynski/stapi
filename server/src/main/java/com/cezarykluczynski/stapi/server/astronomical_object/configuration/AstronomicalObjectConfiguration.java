package com.cezarykluczynski.stapi.server.astronomical_object.configuration;

import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectRestEndpoint;
import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectSoapEndpoint;
import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectV2RestEndpoint;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseRestMapper;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseSoapMapper;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullRestMapper;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class AstronomicalObjectConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint astronomicalObjectEndpoint() {
		return endpointFactory.createSoapEndpoint(AstronomicalObjectSoapEndpoint.class, AstronomicalObjectSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server astronomicalObjectServer() {
		return endpointFactory.createRestEndpoint(AstronomicalObjectRestEndpoint.class, AstronomicalObjectRestEndpoint.ADDRESS);
	}

	@Bean
	public Server astronomicalObjectV2Server() {
		return endpointFactory.createRestEndpoint(AstronomicalObjectV2RestEndpoint.class, AstronomicalObjectV2RestEndpoint.ADDRESS);
	}

	@Bean
	public AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper() {
		return Mappers.getMapper(AstronomicalObjectBaseSoapMapper.class);
	}

	@Bean
	public AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper() {
		return Mappers.getMapper(AstronomicalObjectFullSoapMapper.class);
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
