package com.cezarykluczynski.stapi.server.weapon.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.weapon.endpoint.WeaponRestEndpoint;
import com.cezarykluczynski.stapi.server.weapon.endpoint.WeaponV2RestEndpoint;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseRestMapper;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeaponConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server weaponServer() {
		return endpointFactory.createRestEndpoint(WeaponRestEndpoint.class, WeaponRestEndpoint.ADDRESS);
	}

	@Bean
	public Server weaponV2Server() {
		return endpointFactory.createRestEndpoint(WeaponV2RestEndpoint.class, WeaponV2RestEndpoint.ADDRESS);
	}

	@Bean
	public WeaponBaseRestMapper weaponBaseRestMapper() {
		return Mappers.getMapper(WeaponBaseRestMapper.class);
	}

	@Bean
	public WeaponFullRestMapper weaponFullRestMapper() {
		return Mappers.getMapper(WeaponFullRestMapper.class);
	}

}
