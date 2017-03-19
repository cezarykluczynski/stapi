package com.cezarykluczynski.stapi.server.character.configuration;

import com.cezarykluczynski.stapi.server.character.endpoint.CharacterRestEndpoint;
import com.cezarykluczynski.stapi.server.character.endpoint.CharacterSoapEndpoint;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullSoapMapper;
import com.cezarykluczynski.stapi.server.character.reader.CharacterRestReader;
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
	public CharacterRestEndpoint characterRestEndpoint() {
		return new CharacterRestEndpoint(applicationContext.getBean(CharacterRestReader.class));
	}

	@Bean
	public CharacterBaseSoapMapper characterBaseSoapMapper() {
		return Mappers.getMapper(CharacterBaseSoapMapper.class);
	}

	@Bean
	public CharacterFullSoapMapper characterFullSoapMapper() {
		return Mappers.getMapper(CharacterFullSoapMapper.class);
	}

	@Bean
	public CharacterBaseRestMapper characterBaseRestMapper() {
		return Mappers.getMapper(CharacterBaseRestMapper.class);
	}

	@Bean
	public CharacterFullRestMapper characterFullRestMapper() {
		return Mappers.getMapper(CharacterFullRestMapper.class);
	}

}
