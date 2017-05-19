package com.cezarykluczynski.stapi.server.location.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.location.endpoint.LocationRestEndpoint;
import com.cezarykluczynski.stapi.server.location.endpoint.LocationSoapEndpoint;
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper;
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullRestMapper;
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class LocationConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint locationEndpoint() {
		return endpointFactory.createSoapEndpoint(LocationSoapEndpoint.class, LocationSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server locationServer() {
		return endpointFactory.createRestEndpoint(LocationRestEndpoint.class, LocationRestEndpoint.ADDRESS);
	}

	@Bean
	public LocationBaseSoapMapper locationBaseSoapMapper() {
		return Mappers.getMapper(LocationBaseSoapMapper.class);
	}

	@Bean
	public LocationFullSoapMapper locationFullSoapMapper() {
		return Mappers.getMapper(LocationFullSoapMapper.class);
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
