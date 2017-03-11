package com.cezarykluczynski.stapi.server.astronomicalObject.configuration;

import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectRestEndpoint;
import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectSoapEndpoint;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectRestMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectSoapMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.reader.AstronomicalObjectRestReader;
import com.cezarykluczynski.stapi.server.astronomicalObject.reader.AstronomicalObjectSoapReader;
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
public class AstronomicalObjectConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint astronomicalObjectSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new AstronomicalObjectSoapEndpoint(applicationContext.getBean(AstronomicalObjectSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/astronomicalObject");
		return endpoint;
	}

	@Bean
	public AstronomicalObjectRestEndpoint astronomicalObjectRestEndpoint() {
		return new AstronomicalObjectRestEndpoint(applicationContext.getBean(AstronomicalObjectRestReader.class));
	}

	@Bean
	public AstronomicalObjectSoapMapper astronomicalObjectSoapMapper() {
		return Mappers.getMapper(AstronomicalObjectSoapMapper.class);
	}

	@Bean
	public AstronomicalObjectRestMapper astronomicalObjectRestMapper() {
		return Mappers.getMapper(AstronomicalObjectRestMapper.class);
	}

}
