package com.cezarykluczynski.stapi.server.weapon.mapper

import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.util.AbstractWeaponTest

abstract class AbstractWeaponMapperTest extends AbstractWeaponTest {

	protected static Weapon createWeapon() {
		new Weapon(
				uid: UID,
				name: NAME,
				handHeldWeapon: HAND_HELD_WEAPON,
				laserTechnology: LASER_TECHNOLOGY,
				plasmaTechnology: PLASMA_TECHNOLOGY,
				photonicTechnology: PHOTONIC_TECHNOLOGY,
				phaserTechnology: PHASER_TECHNOLOGY,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)
	}

}
