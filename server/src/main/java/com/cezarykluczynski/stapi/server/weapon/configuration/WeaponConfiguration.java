package com.cezarykluczynski.stapi.server.weapon.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.weapon.endpoint.WeaponRestEndpoint;
import com.cezarykluczynski.stapi.server.weapon.endpoint.WeaponSoapEndpoint;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseRestMapper;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseSoapMapper;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullRestMapper;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class WeaponConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint weaponEndpoint() {
		return endpointFactory.createSoapEndpoint(WeaponSoapEndpoint.class, WeaponSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server weaponServer() {
		return endpointFactory.createRestEndpoint(WeaponRestEndpoint.class, WeaponRestEndpoint.ADDRESS);
	}

	@Bean
	public WeaponBaseSoapMapper weaponBaseSoapMapper() {
		return Mappers.getMapper(WeaponBaseSoapMapper.class);
	}

	@Bean
	public WeaponFullSoapMapper weaponFullSoapMapper() {
		return Mappers.getMapper(WeaponFullSoapMapper.class);
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
