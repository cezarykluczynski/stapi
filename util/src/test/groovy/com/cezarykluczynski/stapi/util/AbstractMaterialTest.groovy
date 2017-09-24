package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractMaterialTest extends AbstractTest {

	protected static final String UID = 'UID'
	protected static final String NAME = 'NAME'
	protected static final boolean CHEMICAL_COMPOUND = RandomUtil.nextBoolean()
	protected static final boolean BIOCHEMICAL_COMPOUND = RandomUtil.nextBoolean()
	protected static final boolean DRUG = RandomUtil.nextBoolean()
	protected static final boolean POISONOUS_SUBSTANCE = RandomUtil.nextBoolean()
	protected static final boolean EXPLOSIVE = RandomUtil.nextBoolean()
	protected static final boolean GEMSTONE = RandomUtil.nextBoolean()
	protected static final boolean ALLOY_OR_COMPOSITE = RandomUtil.nextBoolean()
	protected static final boolean FUEL = RandomUtil.nextBoolean()
	protected static final boolean MINERAL = RandomUtil.nextBoolean()
	protected static final boolean PRECIOUS_MATERIAL = RandomUtil.nextBoolean()

}
