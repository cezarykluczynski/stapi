package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.WeaponPortType
import spock.lang.Specification

class WeaponTest extends Specification {

	private WeaponPortType weaponPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Weapon weapon

	void setup() {
		weaponPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		weapon = new Weapon(weaponPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		WeaponBaseRequest weaponBaseRequest = Mock()
		WeaponBaseResponse weaponBaseResponse = Mock()

		when:
		WeaponBaseResponse weaponBaseResponseOutput = weapon.search(weaponBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(weaponBaseRequest)
		1 * weaponPortTypeMock.getWeaponBase(weaponBaseRequest) >> weaponBaseResponse
		0 * _
		weaponBaseResponse == weaponBaseResponseOutput
	}

	void "searches entities"() {
		given:
		WeaponFullRequest weaponFullRequest = Mock()
		WeaponFullResponse weaponFullResponse = Mock()

		when:
		WeaponFullResponse weaponFullResponseOutput = weapon.get(weaponFullRequest)

		then:
		1 * apiKeySupplierMock.supply(weaponFullRequest)
		1 * weaponPortTypeMock.getWeaponFull(weaponFullRequest) >> weaponFullResponse
		0 * _
		weaponFullResponse == weaponFullResponseOutput
	}

}
