package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class AbstractSpacecraftTest extends Specification {

	protected static final String UID = 'ABCD0987654321'
	protected static final String NAME = 'NAME'
	protected static final Integer NUMBER_OF_DECKS = 42
	protected static final Boolean WARP_CAPABLE = RandomUtil.nextBoolean()
	protected static final String ACTIVE_FROM = 'ACTIVE_FROM'
	protected static final String ACTIVE_TO = 'ACTIVE_TO'

}
