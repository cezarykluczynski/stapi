package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.WeaponApi
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractWeaponTest

class WeaponTest extends AbstractWeaponTest {

	private WeaponApi weaponApiMock

	private Weapon weapon

	void setup() {
		weaponApiMock = Mock()
		weapon = new Weapon(weaponApiMock)
	}

	void "gets single entity"() {
		given:
		WeaponFullResponse weaponFullResponse = Mock()

		when:
		WeaponFullResponse weaponFullResponseOutput = weapon.get(UID)

		then:
		1 * weaponApiMock.v1RestWeaponGet(UID, null) >> weaponFullResponse
		0 * _
		weaponFullResponse == weaponFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		WeaponV2FullResponse weaponV2FullResponse = Mock()

		when:
		WeaponV2FullResponse weaponV2FullResponseOutput = weapon.getV2(UID)

		then:
		1 * weaponApiMock.v2RestWeaponGet(UID) >> weaponV2FullResponse
		0 * _
		weaponV2FullResponse == weaponV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		WeaponBaseResponse weaponBaseResponse = Mock()

		when:
		WeaponBaseResponse weaponBaseResponseOutput = weapon.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, HAND_HELD_WEAPON, LASER_TECHNOLOGY,
				PLASMA_TECHNOLOGY, PHOTONIC_TECHNOLOGY, PHASER_TECHNOLOGY, MIRROR, ALTERNATE_REALITY)

		then:
		1 * weaponApiMock.v1RestWeaponSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, NAME, HAND_HELD_WEAPON, LASER_TECHNOLOGY, PLASMA_TECHNOLOGY,
				PHOTONIC_TECHNOLOGY, PHASER_TECHNOLOGY, MIRROR, ALTERNATE_REALITY) >> weaponBaseResponse
		0 * _
		weaponBaseResponse == weaponBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		WeaponV2BaseResponse weaponV2BaseResponse = Mock()

		when:
		WeaponV2BaseResponse weaponV2BaseResponseOutput = weapon.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, HAND_HELD_WEAPON, LASER_TECHNOLOGY,
				PLASMA_TECHNOLOGY, PHOTONIC_TECHNOLOGY, PHASER_TECHNOLOGY, DIRECTED_ENERGY_WEAPON, EXPLOSIVE_WEAPON, PROJECTILE_WEAPON,
				FICTIONAL_WEAPON, MIRROR, ALTERNATE_REALITY)

		then:
		1 * weaponApiMock.v2RestWeaponSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, HAND_HELD_WEAPON, LASER_TECHNOLOGY, PLASMA_TECHNOLOGY,
				PHOTONIC_TECHNOLOGY, PHASER_TECHNOLOGY, DIRECTED_ENERGY_WEAPON, EXPLOSIVE_WEAPON, PROJECTILE_WEAPON, FICTIONAL_WEAPON, MIRROR,
				ALTERNATE_REALITY) >> weaponV2BaseResponse
		0 * _
		weaponV2BaseResponse == weaponV2BaseResponseOutput
	}

}
