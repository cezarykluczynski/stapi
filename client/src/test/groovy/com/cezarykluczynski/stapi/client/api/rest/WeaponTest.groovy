package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.WeaponApi
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse
import com.cezarykluczynski.stapi.util.AbstractWeaponTest

class WeaponTest extends AbstractWeaponTest {

	private WeaponApi weaponApiMock

	private Weapon weapon

	void setup() {
		weaponApiMock = Mock()
		weapon = new Weapon(weaponApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		WeaponFullResponse weaponFullResponse = Mock()

		when:
		WeaponFullResponse weaponFullResponseOutput = weapon.get(UID)

		then:
		1 * weaponApiMock.weaponGet(UID, API_KEY) >> weaponFullResponse
		0 * _
		weaponFullResponse == weaponFullResponseOutput
	}

	void "searches entities"() {
		given:
		WeaponBaseResponse weaponBaseResponse = Mock()

		when:
		WeaponBaseResponse weaponBaseResponseOutput = weapon.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, HAND_HELD_WEAPON, LASER_TECHNOLOGY,
				PLASMA_TECHNOLOGY, PHOTONIC_TECHNOLOGY, PHASER_TECHNOLOGY, MIRROR, ALTERNATE_REALITY)

		then:
		1 * weaponApiMock.weaponSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, HAND_HELD_WEAPON, LASER_TECHNOLOGY, PLASMA_TECHNOLOGY,
				PHOTONIC_TECHNOLOGY, PHASER_TECHNOLOGY, MIRROR, ALTERNATE_REALITY) >> weaponBaseResponse
		0 * _
		weaponBaseResponse == weaponBaseResponseOutput
	}

}
