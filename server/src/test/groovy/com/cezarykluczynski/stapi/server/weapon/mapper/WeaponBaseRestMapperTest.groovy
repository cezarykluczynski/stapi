package com.cezarykluczynski.stapi.server.weapon.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBase
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class WeaponBaseRestMapperTest extends AbstractWeaponMapperTest {

	private WeaponBaseRestMapper weaponBaseRestMapper

	void setup() {
		weaponBaseRestMapper = Mappers.getMapper(WeaponBaseRestMapper)
	}

	void "maps WeaponRestBeanParams to WeaponRequestDTO"() {
		given:
		WeaponRestBeanParams weaponRestBeanParams = new WeaponRestBeanParams(
				name: NAME,
				handHeldWeapon: HAND_HELD_WEAPON,
				laserTechnology: LASER_TECHNOLOGY,
				plasmaTechnology: PLASMA_TECHNOLOGY,
				photonicTechnology: PHOTONIC_TECHNOLOGY,
				phaserTechnology: PHASER_TECHNOLOGY,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)

		when:
		WeaponRequestDTO weaponRequestDTO = weaponBaseRestMapper.mapBase weaponRestBeanParams

		then:
		weaponRequestDTO.name == NAME
		weaponRequestDTO.handHeldWeapon == HAND_HELD_WEAPON
		weaponRequestDTO.laserTechnology == LASER_TECHNOLOGY
		weaponRequestDTO.plasmaTechnology == PLASMA_TECHNOLOGY
		weaponRequestDTO.photonicTechnology == PHOTONIC_TECHNOLOGY
		weaponRequestDTO.phaserTechnology == PHASER_TECHNOLOGY
		weaponRequestDTO.mirror == MIRROR
		weaponRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base REST entity"() {
		given:
		Weapon weapon = createWeapon()

		when:
		WeaponBase weaponBase = weaponBaseRestMapper.mapBase(Lists.newArrayList(weapon))[0]

		then:
		weaponBase.uid == UID
		weaponBase.name == NAME
		weaponBase.handHeldWeapon == HAND_HELD_WEAPON
		weaponBase.laserTechnology == LASER_TECHNOLOGY
		weaponBase.plasmaTechnology == PLASMA_TECHNOLOGY
		weaponBase.photonicTechnology == PHOTONIC_TECHNOLOGY
		weaponBase.phaserTechnology == PHASER_TECHNOLOGY
		weaponBase.mirror == MIRROR
		weaponBase.alternateReality == ALTERNATE_REALITY
	}

}
