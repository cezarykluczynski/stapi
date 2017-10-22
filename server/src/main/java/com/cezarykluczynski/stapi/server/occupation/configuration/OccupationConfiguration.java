package com.cezarykluczynski.stapi.server.occupation.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.occupation.endpoint.OccupationRestEndpoint;
import com.cezarykluczynski.stapi.server.occupation.endpoint.OccupationSoapEndpoint;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullRestMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

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
