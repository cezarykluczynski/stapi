package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractAnimalTest extends AbstractTest {

	protected static final String NAME = 'NAME'
	protected static final String UID = 'UID'
	protected static final boolean EARTH_ANIMAL = RandomUtil.nextBoolean()
	protected static final boolean EARTH_INSECT = RandomUtil.nextBoolean()
	protected static final boolean AVIAN = RandomUtil.nextBoolean()
	protected static final boolean CANINE = RandomUtil.nextBoolean()
	protected static final boolean FELINE = RandomUtil.nextBoolean()

}
