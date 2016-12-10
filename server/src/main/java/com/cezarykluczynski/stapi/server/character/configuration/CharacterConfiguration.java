package com.cezarykluczynski.stapi.server.character.configuration;

import com.cezarykluczynski.stapi.server.character.endpoint.CharacterSoapEndpoint;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterSoapMapper;
import com.cezarykluczynski.stapi.server.character.reader.CharacterSoapReader;
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
public class CharacterConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint characterSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new CharacterSoapEndpoint(applicationContext.getBean(CharacterSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/character");
		return endpoint;
	}

	@Bean
	public CharacterSoapMapper characterSoapMapper() {
		return Mappers.getMapper(CharacterSoapMapper.class);
	}

	@Bean
	public CharacterRestMapper characterRestMapper() {
		return Mappers.getMapper(CharacterRestMapper.class);
	}

}
