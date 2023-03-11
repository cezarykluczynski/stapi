package com.cezarykluczynski.stapi.server.weapon.mapper

import com.cezarykluczynski.stapi.client.rest.model.WeaponBase
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2Base
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponV2RestBeanParams
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

	void "maps WeaponV2RestBeanParams to WeaponRequestDTO"() {
		given:
		WeaponV2RestBeanParams weaponV2RestBeanParams = new WeaponV2RestBeanParams(
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

		when:
		WeaponRequestDTO weaponRequestDTO = weaponBaseRestMapper.mapV2Base weaponV2RestBeanParams

		then:
		weaponRequestDTO.name == NAME
		weaponRequestDTO.handHeldWeapon == HAND_HELD_WEAPON
		weaponRequestDTO.laserTechnology == LASER_TECHNOLOGY
		weaponRequestDTO.plasmaTechnology == PLASMA_TECHNOLOGY
		weaponRequestDTO.photonicTechnology == PHOTONIC_TECHNOLOGY
		weaponRequestDTO.phaserTechnology == PHASER_TECHNOLOGY
		weaponRequestDTO.directedEnergyWeapon == DIRECTED_ENERGY_WEAPON
		weaponRequestDTO.explosiveWeapon == EXPLOSIVE_WEAPON
		weaponRequestDTO.projectileWeapon == PROJECTILE_WEAPON
		weaponRequestDTO.fictionalWeapon == FICTIONAL_WEAPON
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

	void "maps DB entity to base REST V2 entity"() {
		given:
		Weapon weapon = createWeapon()

		when:
		WeaponV2Base weaponV2Base = weaponBaseRestMapper.mapV2Base(Lists.newArrayList(weapon))[0]

		then:
		weaponV2Base.uid == UID
		weaponV2Base.name == NAME
		weaponV2Base.handHeldWeapon == HAND_HELD_WEAPON
		weaponV2Base.laserTechnology == LASER_TECHNOLOGY
		weaponV2Base.plasmaTechnology == PLASMA_TECHNOLOGY
		weaponV2Base.photonicTechnology == PHOTONIC_TECHNOLOGY
		weaponV2Base.phaserTechnology == PHASER_TECHNOLOGY
		weaponV2Base.directedEnergyWeapon == DIRECTED_ENERGY_WEAPON
		weaponV2Base.explosiveWeapon == EXPLOSIVE_WEAPON
		weaponV2Base.projectileWeapon == PROJECTILE_WEAPON
		weaponV2Base.fictionalWeapon == FICTIONAL_WEAPON
		weaponV2Base.mirror == MIRROR
		weaponV2Base.alternateReality == ALTERNATE_REALITY
	}

}
