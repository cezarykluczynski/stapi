package com.cezarykluczynski.stapi.server.occupation.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.occupation.endpoint.OccupationRestEndpoint;
import com.cezarykluczynski.stapi.server.occupation.endpoint.OccupationSoapEndpoint;
import com.cezarykluczynski.stapi.server.occupation.endpoint.OccupationV2RestEndpoint;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullRestMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullSoapMapper;
import jakarta.inject.Inject;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OccupationConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint occupationEndpoint() {
		return endpointFactory.createSoapEndpoint(OccupationSoapEndpoint.class, OccupationSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server occupationServer() {
		return endpointFactory.createRestEndpoint(OccupationRestEndpoint.class, OccupationRestEndpoint.ADDRESS);
	}

	@Bean
	public Server occupationV2Server() {
		return endpointFactory.createRestEndpoint(OccupationV2RestEndpoint.class, OccupationV2RestEndpoint.ADDRESS);
	}

	@Bean
	public OccupationBaseSoapMapper occupationBaseSoapMapper() {
		return Mappers.getMapper(OccupationBaseSoapMapper.class);
	}

	@Bean
	public OccupationFullSoapMapper occupationFullSoapMapper() {
		return Mappers.getMapper(OccupationFullSoapMapper.class);
	}

	@Bean
	public OccupationBaseRestMapper occupationBaseRestMapper() {
		return Mappers.getMapper(OccupationBaseRestMapper.class);
	}

	@Bean
	public OccupationFullRestMapper occupationFullRestMapper() {
		return Mappers.getMapper(OccupationFullRestMapper.class);
	}

}
