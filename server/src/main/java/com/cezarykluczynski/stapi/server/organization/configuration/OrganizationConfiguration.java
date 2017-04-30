package com.cezarykluczynski.stapi.server.organization.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationRestEndpoint;
import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationSoapEndpoint;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullRestMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class OrganizationConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint organizationEndpoint() {
		return endpointFactory.createSoapEndpoint(OrganizationSoapEndpoint.class, OrganizationSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server organizationServer() {
		return endpointFactory.createRestEndpoint(OrganizationRestEndpoint.class, OrganizationRestEndpoint.ADDRESS);
	}

	@Bean
	public OrganizationBaseSoapMapper organizationBaseSoapMapper() {
		return Mappers.getMapper(OrganizationBaseSoapMapper.class);
	}

	@Bean
	public OrganizationFullSoapMapper organizationFullSoapMapper() {
		return Mappers.getMapper(OrganizationFullSoapMapper.class);
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
