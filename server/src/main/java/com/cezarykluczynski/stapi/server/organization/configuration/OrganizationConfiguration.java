package com.cezarykluczynski.stapi.server.organization.configuration;

import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationRestEndpoint;
import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationSoapEndpoint;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullRestMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullSoapMapper;
import com.cezarykluczynski.stapi.server.organization.reader.OrganizationRestReader;
import com.cezarykluczynski.stapi.server.organization.reader.OrganizationSoapReader;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class OrganizationConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint organizationSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new OrganizationSoapEndpoint(applicationContext.getBean(OrganizationSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/organization");
		return endpoint;
	}

	@Bean
	public OrganizationRestEndpoint organizationRestEndpoint() {
		return new OrganizationRestEndpoint(applicationContext.getBean(OrganizationRestReader.class));
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
