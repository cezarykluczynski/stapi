package com.cezarykluczynski.stapi.server.weapon.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullResponse
import com.cezarykluczynski.stapi.server.weapon.reader.WeaponSoapReader
import spock.lang.Specification

class WeaponSoapEndpointTest extends Specification {

	private WeaponSoapReader weaponSoapReaderMock

	private WeaponSoapEndpoint weaponSoapEndpoint

	void setup() {
		weaponSoapReaderMock = Mock()
		weaponSoapEndpoint = new WeaponSoapEndpoint(weaponSoapReaderMock)
	}

	void "passes base call to WeaponSoapReader"() {
		given:
		WeaponBaseRequest weaponRequest = Mock()
		WeaponBaseResponse weaponResponse = Mock()

		when:
		WeaponBaseResponse weaponResponseResult = weaponSoapEndpoint.getWeaponBase(weaponRequest)

		then:
		1 * weaponSoapReaderMock.readBase(weaponRequest) >> weaponResponse
		weaponResponseResult == weaponResponse
	}

	void "passes full call to WeaponSoapReader"() {
		given:
		WeaponFullRequest weaponFullRequest = Mock()
		WeaponFullResponse weaponFullResponse = Mock()

		when:
		WeaponFullResponse weaponResponseResult = weaponSoapEndpoint.getWeaponFull(weaponFullRequest)

		then:
		1 * weaponSoapReaderMock.readFull(weaponFullRequest) >> weaponFullResponse
		weaponResponseResult == weaponFullResponse
	}

}
