package com.cezarykluczynski.stapi.server.spacecraft.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.spacecraft.endpoint.SpacecraftRestEndpoint;
import com.cezarykluczynski.stapi.server.spacecraft.endpoint.SpacecraftSoapEndpoint;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class SpacecraftConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint spacecraftEndpoint() {
		return endpointFactory.createSoapEndpoint(SpacecraftSoapEndpoint.class, SpacecraftSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server spacecraftServer() {
		return endpointFactory.createRestEndpoint(SpacecraftRestEndpoint.class, SpacecraftRestEndpoint.ADDRESS);
	}

	@Bean
	public SpacecraftBaseSoapMapper spacecraftBaseSoapMapper() {
		return Mappers.getMapper(SpacecraftBaseSoapMapper.class);
	}

	@Bean
	public SpacecraftFullSoapMapper spacecraftFullSoapMapper() {
		return Mappers.getMapper(SpacecraftFullSoapMapper.class);
	}

	@Bean
	public SpacecraftBaseRestMapper spacecraftBaseRestMapper() {
		return Mappers.getMapper(SpacecraftBaseRestMapper.class);
	}

	@Bean
	public SpacecraftFullRestMapper spacecraftFullRestMapper() {
		return Mappers.getMapper(SpacecraftFullRestMapper.class);
	}

}
