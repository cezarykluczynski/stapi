package com.cezarykluczynski.stapi.server.conflict.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.conflict.endpoint.ConflictRestEndpoint;
import com.cezarykluczynski.stapi.server.conflict.endpoint.ConflictSoapEndpoint;
import com.cezarykluczynski.stapi.server.conflict.endpoint.ConflictV2RestEndpoint;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseRestMapper;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseSoapMapper;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullRestMapper;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullSoapMapper;
import jakarta.inject.Inject;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConflictConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint conflictEndpoint() {
		return endpointFactory.createSoapEndpoint(ConflictSoapEndpoint.class, ConflictSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server conflictServer() {
		return endpointFactory.createRestEndpoint(ConflictRestEndpoint.class, ConflictRestEndpoint.ADDRESS);
	}

	@Bean
	public Server conflictV2Server() {
		return endpointFactory.createRestEndpoint(ConflictV2RestEndpoint.class, ConflictV2RestEndpoint.ADDRESS);
	}

	@Bean
	public ConflictBaseSoapMapper conflictBaseSoapMapper() {
		return Mappers.getMapper(ConflictBaseSoapMapper.class);
	}

	@Bean
	public ConflictFullSoapMapper conflictFullSoapMapper() {
		return Mappers.getMapper(ConflictFullSoapMapper.class);
	}

	@Bean
	public ConflictBaseRestMapper conflictBaseRestMapper() {
		return Mappers.getMapper(ConflictBaseRestMapper.class);
	}

	@Bean
	public ConflictFullRestMapper conflictFullRestMapper() {
		return Mappers.getMapper(ConflictFullRestMapper.class);
	}

}
