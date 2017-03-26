package com.cezarykluczynski.stapi.server.company.configuration;

import com.cezarykluczynski.stapi.server.company.endpoint.CompanyRestEndpoint;
import com.cezarykluczynski.stapi.server.company.endpoint.CompanySoapEndpoint;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullSoapMapper;
import com.cezarykluczynski.stapi.server.company.reader.CompanyRestReader;
import com.cezarykluczynski.stapi.server.company.reader.CompanySoapReader;
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
public class CompanyConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint companySoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new CompanySoapEndpoint(applicationContext.getBean(CompanySoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/company");
		return endpoint;
	}

	@Bean
	public CompanyRestEndpoint companyRestEndpoint() {
		return new CompanyRestEndpoint(applicationContext.getBean(CompanyRestReader.class));
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
