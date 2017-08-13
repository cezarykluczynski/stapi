package com.cezarykluczynski.stapi.server.weapon.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFull
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import org.mapstruct.factory.Mappers

class WeaponFullRestMapperTest extends AbstractWeaponMapperTest {

	private WeaponFullRestMapper weaponFullRestMapper

	void setup() {
		weaponFullRestMapper = Mappers.getMapper(WeaponFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Weapon dBWeapon = createWeapon()

		when:
		WeaponFull weaponFull = weaponFullRestMapper.mapFull(dBWeapon)

		then:
		weaponFull.uid == UID
		weaponFull.name == NAME
		weaponFull.handHeldWeapon == HAND_HELD_WEAPON
		weaponFull.laserTechnology == LASER_TECHNOLOGY
		weaponFull.plasmaTechnology == PLASMA_TECHNOLOGY
		weaponFull.photonicTechnology == PHOTONIC_TECHNOLOGY
		weaponFull.phaserTechnology == PHASER_TECHNOLOGY
		weaponFull.mirror == MIRROR
		weaponFull.alternateReality == ALTERNATE_REALITY
	}

}
