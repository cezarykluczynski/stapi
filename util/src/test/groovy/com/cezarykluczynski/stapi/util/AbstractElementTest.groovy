package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractElementTest extends AbstractTest {

	protected static final String NAME = 'NAME'
	protected static final String SYMBOL = 'SYMBOL'
	protected static final String UID = 'UID'
	protected static final Integer ATOMIC_NUMBER = 44
	protected static final Integer ATOMIC_WEIGHT = 66

	protected static final boolean TRANSURANIUM = RandomUtil.nextBoolean()
	protected static final boolean GAMMA_SERIES = RandomUtil.nextBoolean()
	protected static final boolean HYPERSONIC_SERIES = RandomUtil.nextBoolean()
	protected static final boolean MEGA_SERIES = RandomUtil.nextBoolean()
	protected static final boolean OMEGA_SERIES = RandomUtil.nextBoolean()
	protected static final boolean TRANSONIC_SERIES = RandomUtil.nextBoolean()
	protected static final boolean WORLD_SERIES = RandomUtil.nextBoolean()

}
