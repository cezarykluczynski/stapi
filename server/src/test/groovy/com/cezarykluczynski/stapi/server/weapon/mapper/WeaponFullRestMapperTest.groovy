package com.cezarykluczynski.stapi.server.weapon.mapper

import com.cezarykluczynski.stapi.client.rest.model.WeaponFull
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2Full
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		Weapon dBWeapon = createWeapon()

		when:
		WeaponV2Full weaponV2Full = weaponFullRestMapper.mapV2Full(dBWeapon)

		then:
		weaponV2Full.uid == UID
		weaponV2Full.name == NAME
		weaponV2Full.handHeldWeapon == HAND_HELD_WEAPON
		weaponV2Full.laserTechnology == LASER_TECHNOLOGY
		weaponV2Full.plasmaTechnology == PLASMA_TECHNOLOGY
		weaponV2Full.photonicTechnology == PHOTONIC_TECHNOLOGY
		weaponV2Full.phaserTechnology == PHASER_TECHNOLOGY
		weaponV2Full.directedEnergyWeapon == DIRECTED_ENERGY_WEAPON
		weaponV2Full.explosiveWeapon == EXPLOSIVE_WEAPON
		weaponV2Full.projectileWeapon == PROJECTILE_WEAPON
		weaponV2Full.fictionalWeapon == FICTIONAL_WEAPON
		weaponV2Full.mirror == MIRROR
		weaponV2Full.alternateReality == ALTERNATE_REALITY
	}

}
