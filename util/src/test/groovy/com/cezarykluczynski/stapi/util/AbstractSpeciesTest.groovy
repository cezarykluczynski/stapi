package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.LogicUtil
import spock.lang.Specification

class AbstractSpeciesTest extends Specification {

	protected static final String NAME = 'NAME'
	protected static final String GUID = 'GUID'
	protected static final Boolean EXTINCT_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean WARP_CAPABLE_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean EXTRA_GALACTIC_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean HUMANOID_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean REPTILIAN_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean NON_CORPOREAL_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean SHAPESHIFTING_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean SPACEBORNE_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean TELEPATHIC_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean TRANS_DIMENSIONAL_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean UNNAMED_SPECIES = LogicUtil.nextBoolean()
	protected static final Boolean ALTERNATE_REALITY = LogicUtil.nextBoolean()

}
