package com.cezarykluczynski.stapi.server.weapon.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_WEAPONS)
})
class WeaponSoapEndpointIntegrationTest extends AbstractWeaponEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets weapon by UID"() {
		when:
		WeaponFullResponse weaponFullResponse = stapiSoapClient.weaponPortType.getWeaponFull(new WeaponFullRequest(
				uid: 'WEMA0000126956'
		))

		then:
		weaponFullResponse.weapon.name == 'Subspace warhead'
	}

	void "'Shotgun' is among hand-held weapons from mirror universe"() {
		when:
		WeaponBaseResponse weaponBaseResponse = stapiSoapClient.weaponPortType.getWeaponBase(new WeaponBaseRequest(
				handHeldWeapon: true,
				mirror: true
		))

		then:
		weaponBaseResponse.weapons
				.stream()
				.anyMatch { it -> it.name == 'Shotgun' }
	}

}
