package com.cezarykluczynski.stapi.server.organization.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationRestEndpoint;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server organizationServer() {
		return endpointFactory.createRestEndpoint(OrganizationRestEndpoint.class, OrganizationRestEndpoint.ADDRESS);
	}

	@Bean
	public OrganizationBaseRestMapper organizationBaseRestMapper() {
		return Mappers.getMapper(OrganizationBaseRestMapper.class);
	}

	@Bean
	public OrganizationFullRestMapper organizationFullRestMapper() {
		return Mappers.getMapper(OrganizationFullRestMapper.class);
	}

}
