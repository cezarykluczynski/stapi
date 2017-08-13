package com.cezarykluczynski.stapi.server.weapon.mapper

import com.cezarykluczynski.stapi.client.v1.soap.WeaponFull
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import org.mapstruct.factory.Mappers

class WeaponFullSoapMapperTest extends AbstractWeaponMapperTest {

	private WeaponFullSoapMapper weaponFullSoapMapper

	void setup() {
		weaponFullSoapMapper = Mappers.getMapper(WeaponFullSoapMapper)
	}

	void "maps SOAP WeaponFullRequest to WeaponBaseRequestDTO"() {
		given:
		WeaponFullRequest weaponFullRequest = new WeaponFullRequest(uid: UID)

		when:
		WeaponRequestDTO weaponRequestDTO = weaponFullSoapMapper.mapFull weaponFullRequest

		then:
		weaponRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Weapon weapon = createWeapon()

		when:
		WeaponFull weaponFull = weaponFullSoapMapper.mapFull(weapon)

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
