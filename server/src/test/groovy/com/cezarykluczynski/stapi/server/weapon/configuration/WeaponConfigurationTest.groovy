package com.cezarykluczynski.stapi.server.weapon.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.weapon.endpoint.WeaponRestEndpoint
import com.cezarykluczynski.stapi.server.weapon.endpoint.WeaponV2RestEndpoint
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseRestMapper
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class WeaponConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private WeaponConfiguration weaponConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		weaponConfiguration = new WeaponConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Weapon REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = weaponConfiguration.weaponServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(WeaponRestEndpoint, WeaponRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Weapon V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = weaponConfiguration.weaponV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(WeaponV2RestEndpoint, WeaponV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "WeaponBaseRestMapper is created"() {
		when:
		WeaponBaseRestMapper weaponBaseRestMapper = weaponConfiguration.weaponBaseRestMapper()

		then:
		weaponBaseRestMapper != null
	}

	void "WeaponFullRestMapper is created"() {
		when:
		WeaponFullRestMapper weaponFullRestMapper = weaponConfiguration.weaponFullRestMapper()

		then:
		weaponFullRestMapper != null
	}

}
