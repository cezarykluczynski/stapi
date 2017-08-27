package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractSpacecraftClassTest extends AbstractTest {

	protected static final String UID = 'UID'
	protected static final String NAME = 'NAME'
	protected static final Integer NUMBER_OF_DECKS = 42
	protected static final Boolean WARP_CAPABLE = RandomUtil.nextBoolean()
	protected static final Boolean ALTERNATE_REALITY = RandomUtil.nextBoolean()
	protected static final String ACTIVE_FROM = 'ACTIVE_FROM'
	protected static final String ACTIVE_TO = 'ACTIVE_TO'

}
