package com.cezarykluczynski.stapi.server.company.configuration;

import com.cezarykluczynski.stapi.server.company.endpoint.CompanySoapEndpoint;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanySoapMapper;
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
	public CompanySoapMapper companySoapMapper() {
		return Mappers.getMapper(CompanySoapMapper.class);
	}

	@Bean
	public CompanyRestMapper companyRestMapper() {
		return Mappers.getMapper(CompanyRestMapper.class);
	}

}
