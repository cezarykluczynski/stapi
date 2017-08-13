package com.cezarykluczynski.stapi.server.weapon.mapper

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBase
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class WeaponBaseSoapMapperTest extends AbstractWeaponMapperTest {

	private WeaponBaseSoapMapper weaponBaseSoapMapper

	void setup() {
		weaponBaseSoapMapper = Mappers.getMapper(WeaponBaseSoapMapper)
	}

	void "maps SOAP WeaponBaseRequest to WeaponRequestDTO"() {
		given:
		WeaponBaseRequest weaponBaseRequest = new WeaponBaseRequest(
				name: NAME,
				handHeldWeapon: HAND_HELD_WEAPON,
				laserTechnology: LASER_TECHNOLOGY,
				plasmaTechnology: PLASMA_TECHNOLOGY,
				photonicTechnology: PHOTONIC_TECHNOLOGY,
				phaserTechnology: PHASER_TECHNOLOGY,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)

		when:
		WeaponRequestDTO weaponRequestDTO = weaponBaseSoapMapper.mapBase weaponBaseRequest

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

	void "maps DB entity to base SOAP entity"() {
		given:
		Weapon weapon = createWeapon()

		when:
		WeaponBase weaponBase = weaponBaseSoapMapper.mapBase(Lists.newArrayList(weapon))[0]

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
