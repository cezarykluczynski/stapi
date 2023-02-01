package com.cezarykluczynski.stapi.server.location.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.location.endpoint.LocationRestEndpoint;
import com.cezarykluczynski.stapi.server.location.endpoint.LocationV2RestEndpoint;
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper;
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server locationServer() {
		return endpointFactory.createRestEndpoint(LocationRestEndpoint.class, LocationRestEndpoint.ADDRESS);
	}

	@Bean
	public Server locationV2Server() {
		return endpointFactory.createRestEndpoint(LocationV2RestEndpoint.class, LocationV2RestEndpoint.ADDRESS);
	}

	@Bean
	public LocationBaseRestMapper locationBaseRestMapper() {
		return Mappers.getMapper(LocationBaseRestMapper.class);
	}

	@Bean
	public LocationFullRestMapper locationFullRestMapper() {
		return Mappers.getMapper(LocationFullRestMapper.class);
	}

}
