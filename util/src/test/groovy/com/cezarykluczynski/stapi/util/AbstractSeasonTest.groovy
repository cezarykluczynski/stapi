package com.cezarykluczynski.stapi.util

import org.apache.commons.lang3.RandomUtils

abstract class AbstractSeasonTest extends AbstractTest {

	protected static final String UID = 'UID'
	protected static final String TITLE = 'TITLE'
	protected static final Integer SEASON_NUMBER = 2
	protected static final Integer NUMBER_OF_EPISODES = 13
	protected static final Integer SEASON_NUMBER_FROM = 1
	protected static final Integer SEASON_NUMBER_TO = 3
	protected static final Integer NUMBER_OF_EPISODES_FROM = 11
	protected static final Integer NUMBER_OF_EPISODES_TO = 15
	protected static final Boolean COMPANION_SERIES_SEASON = RandomUtils.nextBoolean()

}
