package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractConflictTest extends AbstractTest {

	protected static final String UID = 'UID'
	protected static final String NAME = 'NAME'
	protected static final Integer YEAR_FROM = 2365
	protected static final Integer YEAR_TO = 2367
	protected static final Boolean EARTH_CONFLICT = RandomUtil.nextBoolean()
	protected static final Boolean FEDERATION_WAR = RandomUtil.nextBoolean()
	protected static final Boolean KLINGON_WAR = RandomUtil.nextBoolean()
	protected static final Boolean DOMINION_WAR_BATTLE = RandomUtil.nextBoolean()
	protected static final Boolean ALTERNATE_REALITY = RandomUtil.nextBoolean()

}
