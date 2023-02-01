package com.cezarykluczynski.stapi.server.company.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.company.endpoint.CompanyRestEndpoint;
import com.cezarykluczynski.stapi.server.company.endpoint.CompanyV2RestEndpoint;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server companyServer() {
		return endpointFactory.createRestEndpoint(CompanyRestEndpoint.class, CompanyRestEndpoint.ADDRESS);
	}

	@Bean
	public Server companyV2Server() {
		return endpointFactory.createRestEndpoint(CompanyV2RestEndpoint.class, CompanyV2RestEndpoint.ADDRESS);
	}

	@Bean
	public CompanyBaseRestMapper companyBaseRestMapper() {
		return Mappers.getMapper(CompanyBaseRestMapper.class);
	}

	@Bean
	public CompanyFullRestMapper companyFullRestMapper() {
		return Mappers.getMapper(CompanyFullRestMapper.class);
	}

}
