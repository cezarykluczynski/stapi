package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.LogicUtil
import spock.lang.Specification

abstract class AbstractIndividualTest extends Specification {

	protected static final String NAME = 'NAME'
	protected static final String GUID = 'GUID'
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
	protected static final Boolean DECEASED = LogicUtil.nextBoolean()
	protected static final Boolean MIRROR = LogicUtil.nextBoolean()
	protected static final Boolean ALTERNATE_REALITY = LogicUtil.nextBoolean()
	protected static final String SERIAL_NUMBER = 'SERIAL_NUMBER'

}
