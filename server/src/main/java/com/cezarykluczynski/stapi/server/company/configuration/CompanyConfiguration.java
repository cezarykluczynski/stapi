package com.cezarykluczynski.stapi.server.company.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.company.endpoint.CompanyRestEndpoint;
import com.cezarykluczynski.stapi.server.company.endpoint.CompanySoapEndpoint;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class CompanyConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint companyEndpoint() {
		return endpointFactory.createSoapEndpoint(CompanySoapEndpoint.class, CompanySoapEndpoint.ADDRESS);
	}

	@Bean
	public Server companyServer() {
		return endpointFactory.createRestEndpoint(CompanyRestEndpoint.class, CompanyRestEndpoint.ADDRESS);
	}

	@Bean
	public CompanyBaseSoapMapper companyBaseSoapMapper() {
		return Mappers.getMapper(CompanyBaseSoapMapper.class);
	}

	@Bean
	public CompanyFullSoapMapper companyFullSoapMapper() {
		return Mappers.getMapper(CompanyFullSoapMapper.class);
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
