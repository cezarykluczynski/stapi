package com.cezarykluczynski.stapi.server.material.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.material.endpoint.MaterialRestEndpoint;
import com.cezarykluczynski.stapi.server.material.endpoint.MaterialSoapEndpoint;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseRestMapper;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseSoapMapper;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullRestMapper;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class MaterialConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint materialEndpoint() {
		return endpointFactory.createSoapEndpoint(MaterialSoapEndpoint.class, MaterialSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server materialServer() {
		return endpointFactory.createRestEndpoint(MaterialRestEndpoint.class, MaterialRestEndpoint.ADDRESS);
	}

	@Bean
	public MaterialBaseSoapMapper materialBaseSoapMapper() {
		return Mappers.getMapper(MaterialBaseSoapMapper.class);
	}

	@Bean
	public MaterialFullSoapMapper materialFullSoapMapper() {
		return Mappers.getMapper(MaterialFullSoapMapper.class);
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
