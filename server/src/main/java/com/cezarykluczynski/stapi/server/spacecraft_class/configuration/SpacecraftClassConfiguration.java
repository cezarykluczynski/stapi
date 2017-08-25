package com.cezarykluczynski.stapi.server.spacecraft_class.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.spacecraft_class.endpoint.SpacecraftClassRestEndpoint;
import com.cezarykluczynski.stapi.server.spacecraft_class.endpoint.SpacecraftClassSoapEndpoint;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class SpacecraftClassConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint spacecraftClassEndpoint() {
		return endpointFactory.createSoapEndpoint(SpacecraftClassSoapEndpoint.class, SpacecraftClassSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server spacecraftClassServer() {
		return endpointFactory.createRestEndpoint(SpacecraftClassRestEndpoint.class, SpacecraftClassRestEndpoint.ADDRESS);
	}

	@Bean
	public SpacecraftClassBaseSoapMapper spacecraftClassBaseSoapMapper() {
		return Mappers.getMapper(SpacecraftClassBaseSoapMapper.class);
	}

	@Bean
	public SpacecraftClassFullSoapMapper spacecraftClassFullSoapMapper() {
		return Mappers.getMapper(SpacecraftClassFullSoapMapper.class);
	}

	@Bean
	public SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper() {
		return Mappers.getMapper(SpacecraftClassBaseRestMapper.class);
	}

	@Bean
	public SpacecraftClassFullRestMapper spacecraftClassFullRestMapper() {
		return Mappers.getMapper(SpacecraftClassFullRestMapper.class);
	}

}
