package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractTitleTest extends AbstractTest {

	protected static final String UID = 'ABCD0987654321'
	protected static final String NAME = 'NAME'
	protected static final Boolean MILITARY_RANK = RandomUtil.nextBoolean()
	protected static final Boolean FLEET_RANK = RandomUtil.nextBoolean()
	protected static final Boolean RELIGIOUS_TITLE = RandomUtil.nextBoolean()
	protected static final Boolean POSITION = RandomUtil.nextBoolean()
	protected static final Boolean MIRROR = RandomUtil.nextBoolean()

}
