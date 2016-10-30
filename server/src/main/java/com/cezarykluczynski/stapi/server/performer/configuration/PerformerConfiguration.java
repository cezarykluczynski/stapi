package com.cezarykluczynski.stapi.server.performer.configuration;

import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerSoapEndpoint;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRequestMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerSoapMapper;
import com.cezarykluczynski.stapi.server.performer.reader.PerformerSoapReader;
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
public class PerformerConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint performerSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new PerformerSoapEndpoint(applicationContext.getBean(PerformerSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/performer");
		return endpoint;
	}

	@Bean
	public PerformerRequestMapper performerRequestMapper() {
		return Mappers.getMapper(PerformerRequestMapper.class);
	}

	@Bean
	public PerformerSoapMapper performerSoapMapper() {
		return Mappers.getMapper(PerformerSoapMapper.class);
	}

}
