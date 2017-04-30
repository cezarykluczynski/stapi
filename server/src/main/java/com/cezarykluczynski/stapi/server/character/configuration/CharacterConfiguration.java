package com.cezarykluczynski.stapi.server.character.configuration;

import com.cezarykluczynski.stapi.server.character.endpoint.CharacterRestEndpoint;
import com.cezarykluczynski.stapi.server.character.endpoint.CharacterSoapEndpoint;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class CharacterConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint characterEndpoint() {
		return endpointFactory.createSoapEndpoint(CharacterSoapEndpoint.class, CharacterSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server characterServer() {
		return endpointFactory.createRestEndpoint(CharacterRestEndpoint.class, CharacterRestEndpoint.ADDRESS);
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
