package com.cezarykluczynski.stapi.server.weapon.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_WEAPONS)
})
class WeaponRestEndpointIntegrationTest extends AbstractWeaponEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets weapon by UID"() {
		when:
		WeaponFullResponse weaponFullResponse = stapiRestClient.weaponApi.weaponGet('WEMA0000066224', null)

		then:
		weaponFullResponse.weapon.name == 'Plasma grenade'
	}

	void "'Plasma phaser' is among plasma technology and phaser technology"() {
		when:
		WeaponBaseResponse weaponBaseResponse = stapiRestClient.weaponApi.weaponSearchPost(null, null, null, null, null, null, null, true, null,
				true, null, null)

		then:
		weaponBaseResponse.weapons
				.stream()
				.anyMatch { it -> it.name == 'Plasma phaser' }
	}

}
