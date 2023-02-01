package com.cezarykluczynski.stapi.server.material.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.material.endpoint.MaterialRestEndpoint;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseRestMapper;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MaterialConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server materialServer() {
		return endpointFactory.createRestEndpoint(MaterialRestEndpoint.class, MaterialRestEndpoint.ADDRESS);
	}

	@Bean
	public MaterialBaseRestMapper materialBaseRestMapper() {
		return Mappers.getMapper(MaterialBaseRestMapper.class);
	}

	@Bean
	public MaterialFullRestMapper materialFullRestMapper() {
		return Mappers.getMapper(MaterialFullRestMapper.class);
	}

}
