package com.cezarykluczynski.stapi.server.staff.configuration;

import com.cezarykluczynski.stapi.server.staff.endpoint.StaffRestEndpoint;
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffSoapEndpoint;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffSoapMapper;
import com.cezarykluczynski.stapi.server.staff.reader.StaffRestReader;
import com.cezarykluczynski.stapi.server.staff.reader.StaffSoapReader;
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
public class StaffConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint staffSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new StaffSoapEndpoint(applicationContext.getBean(StaffSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/staff");
		return endpoint;
	}

	@Bean
	public StaffRestEndpoint staffRestEndpoint() {
		return new StaffRestEndpoint(applicationContext.getBean(StaffRestReader.class));
	}

	@Bean
	public StaffSoapMapper staffSoapMapper() {
		return Mappers.getMapper(StaffSoapMapper.class);
	}

	@Bean
	public StaffRestMapper staffRestMapper() {
		return Mappers.getMapper(StaffRestMapper.class);
	}

}
