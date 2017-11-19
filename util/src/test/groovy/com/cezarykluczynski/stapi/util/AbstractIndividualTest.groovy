package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

abstract class AbstractIndividualTest extends AbstractTest {

	protected static final String NAME = 'NAME'
	protected static final String UID = 'UID'
	protected static final String GENDER = 'GENDER'
	protected static final Integer YEAR_OF_BIRTH = 1965
	protected static final Integer MONTH_OF_BIRTH = 2
	protected static final Integer DAY_OF_BIRTH = 17
	protected static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	protected static final Integer YEAR_OF_DEATH = 1967
	protected static final Integer MONTH_OF_DEATH = 7
	protected static final Integer DAY_OF_DEATH = 19
	protected static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'
	protected static final Integer HEIGHT = 175
	protected static final Integer WEIGHT = 80
	protected static final String HOLOGRAM_ACTIVATION_DATE = 'HOLOGRAM_ACTIVATION_DATE'
	protected static final String HOLOGRAM_STATUS = 'HOLOGRAM_STATUS'
	protected static final String HOLOGRAM_DATE_STATUS = 'HOLOGRAM_DATE_STATUS'
	protected static final Boolean HOLOGRAM = RandomUtil.nextBoolean()
	protected static final Boolean FICTIONAL_CHARACTER = RandomUtil.nextBoolean()
	protected static final Boolean DECEASED = RandomUtil.nextBoolean()
	protected static final Boolean MIRROR = RandomUtil.nextBoolean()
	protected static final Boolean ALTERNATE_REALITY = RandomUtil.nextBoolean()
	protected static final String SERIAL_NUMBER = 'SERIAL_NUMBER'

}
