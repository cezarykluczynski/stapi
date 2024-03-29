package com.cezarykluczynski.stapi.server.character.configuration;

import com.cezarykluczynski.stapi.server.character.endpoint.CharacterRestEndpoint;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullRestMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CharacterConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server characterServer() {
		return endpointFactory.createRestEndpoint(CharacterRestEndpoint.class, CharacterRestEndpoint.ADDRESS);
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
