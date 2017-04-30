package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

abstract class AbstractSpeciesTest extends AbstractTest {

	protected static final String NAME = 'NAME'
	protected static final String UID = 'UID'
	protected static final Boolean EXTINCT_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean WARP_CAPABLE_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean EXTRA_GALACTIC_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean HUMANOID_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean REPTILIAN_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean NON_CORPOREAL_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean SHAPESHIFTING_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean SPACEBORNE_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean TELEPATHIC_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean TRANS_DIMENSIONAL_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean UNNAMED_SPECIES = RandomUtil.nextBoolean()
	protected static final Boolean ALTERNATE_REALITY = RandomUtil.nextBoolean()

}
