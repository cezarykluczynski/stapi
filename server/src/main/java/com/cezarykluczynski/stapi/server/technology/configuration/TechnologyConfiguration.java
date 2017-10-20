package com.cezarykluczynski.stapi.server.technology.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.technology.endpoint.TechnologyRestEndpoint;
import com.cezarykluczynski.stapi.server.technology.endpoint.TechnologySoapEndpoint;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullRestMapper;
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class TechnologyConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint technologyEndpoint() {
		return endpointFactory.createSoapEndpoint(TechnologySoapEndpoint.class, TechnologySoapEndpoint.ADDRESS);
	}

	@Bean
	public Server technologyServer() {
		return endpointFactory.createRestEndpoint(TechnologyRestEndpoint.class, TechnologyRestEndpoint.ADDRESS);
	}

	@Bean
	public TechnologyBaseSoapMapper technologyBaseSoapMapper() {
		return Mappers.getMapper(TechnologyBaseSoapMapper.class);
	}

	@Bean
	public TechnologyFullSoapMapper technologyFullSoapMapper() {
		return Mappers.getMapper(TechnologyFullSoapMapper.class);
	}

	@Bean
	public TechnologyBaseRestMapper technologyBaseRestMapper() {
		return Mappers.getMapper(TechnologyBaseRestMapper.class);
	}

	@Bean
	public TechnologyFullRestMapper technologyFullRestMapper() {
		return Mappers.getMapper(TechnologyFullRestMapper.class);
	}

}
