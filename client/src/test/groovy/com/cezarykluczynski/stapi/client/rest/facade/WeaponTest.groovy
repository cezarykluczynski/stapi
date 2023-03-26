package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.WeaponApi
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractWeaponTest

class WeaponTest extends AbstractWeaponTest {

	private WeaponApi weaponApiMock

	private Weapon weapon

	void setup() {
		weaponApiMock = Mock()
		weapon = new Weapon(weaponApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		WeaponV2FullResponse weaponV2FullResponse = Mock()

		when:
		WeaponV2FullResponse weaponV2FullResponseOutput = weapon.getV2(UID)

		then:
		1 * weaponApiMock.v2GetWeapon(UID) >> weaponV2FullResponse
		0 * _
		weaponV2FullResponse == weaponV2FullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		WeaponV2BaseResponse weaponV2BaseResponse = Mock()
		WeaponV2SearchCriteria weaponV2SearchCriteria = new WeaponV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				handHeldWeapon: HAND_HELD_WEAPON,
				laserTechnology: LASER_TECHNOLOGY,
				plasmaTechnology: PLASMA_TECHNOLOGY,
				photonicTechnology: PHOTONIC_TECHNOLOGY,
				phaserTechnology: PHASER_TECHNOLOGY,
				directedEnergyWeapon: DIRECTED_ENERGY_WEAPON,
				explosiveWeapon: EXPLOSIVE_WEAPON,
				projectileWeapon: PROJECTILE_WEAPON,
				fictionalWeapon: FICTIONAL_WEAPON,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)
		weaponV2SearchCriteria.sort = SORT

		when:
		WeaponV2BaseResponse weaponV2BaseResponseOutput = weapon.searchV2(weaponV2SearchCriteria)

		then:
		1 * weaponApiMock.v2SearchWeapons(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, HAND_HELD_WEAPON, LASER_TECHNOLOGY, PLASMA_TECHNOLOGY,
				PHOTONIC_TECHNOLOGY, PHASER_TECHNOLOGY, DIRECTED_ENERGY_WEAPON, EXPLOSIVE_WEAPON, PROJECTILE_WEAPON, FICTIONAL_WEAPON, MIRROR,
				ALTERNATE_REALITY) >> weaponV2BaseResponse
		0 * _
		weaponV2BaseResponse == weaponV2BaseResponseOutput
	}

}
