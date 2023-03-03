package com.cezarykluczynski.stapi.server.weapon.endpoint

import com.cezarykluczynski.stapi.client.api.dto.WeaponV2SearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_WEAPONS)
})
class WeaponRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets weapon by UID"() {
		when:
		WeaponV2FullResponse weaponV2FullResponse = stapiRestClient.weapon.getV2('WEMA0000066224')

		then:
		weaponV2FullResponse.weapon.name == 'Plasma grenade'
	}

	void "'Plasma phaser' is among plasma technology and phaser technology"() {
		given:
		WeaponV2SearchCriteria weaponV2SearchCriteria = new WeaponV2SearchCriteria(
				plasmaTechnology: true,
				phaserTechnology: true
		)

		when:
		WeaponV2BaseResponse weaponV2BaseResponse = stapiRestClient.weapon.searchV2(weaponV2SearchCriteria)

		then:
		weaponV2BaseResponse.weapons
				.stream()
				.anyMatch { it -> it.name == 'Plasma phaser' }
	}

}
