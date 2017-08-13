package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractWeaponTest extends AbstractTest {

	protected static final String UID = 'ABCD0123456789'
	protected static final String NAME = 'NAME'
	protected static final Boolean HAND_HELD_WEAPON = RandomUtil.nextBoolean()
	protected static final Boolean LASER_TECHNOLOGY = RandomUtil.nextBoolean()
	protected static final Boolean PLASMA_TECHNOLOGY = RandomUtil.nextBoolean()
	protected static final Boolean PHOTONIC_TECHNOLOGY = RandomUtil.nextBoolean()
	protected static final Boolean PHASER_TECHNOLOGY = RandomUtil.nextBoolean()
	protected static final Boolean MIRROR = RandomUtil.nextBoolean()
	protected static final Boolean ALTERNATE_REALITY = RandomUtil.nextBoolean()

}
