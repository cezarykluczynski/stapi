package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.LogicUtil
import spock.lang.Specification

abstract class AbstractLocationTest extends Specification {

	protected static final String UID = 'ABCD0123456789'
	protected static final String NAME = 'NAME'
	protected static final boolean EARTHLY_LOCATION = LogicUtil.nextBoolean()
	protected static final boolean FICTIONAL_LOCATION = LogicUtil.nextBoolean()
	protected static final boolean RELIGIOUS_LOCATION = LogicUtil.nextBoolean()
	protected static final boolean GEOGRAPHICAL_LOCATION = LogicUtil.nextBoolean()
	protected static final boolean BODY_OF_WATER = LogicUtil.nextBoolean()
	protected static final boolean COUNTRY = LogicUtil.nextBoolean()
	protected static final boolean SUBNATIONAL_ENTITY = LogicUtil.nextBoolean()
	protected static final boolean SETTLEMENT = LogicUtil.nextBoolean()
	protected static final boolean US_SETTLEMENT = LogicUtil.nextBoolean()
	protected static final boolean BAJORAN_SETTLEMENT = LogicUtil.nextBoolean()
	protected static final boolean COLONY = LogicUtil.nextBoolean()
	protected static final boolean LANDFORM = LogicUtil.nextBoolean()
	protected static final boolean LANDMARK = LogicUtil.nextBoolean()
	protected static final boolean ROAD = LogicUtil.nextBoolean()
	protected static final boolean STRUCTURE = LogicUtil.nextBoolean()
	protected static final boolean SHIPYARD = LogicUtil.nextBoolean()
	protected static final boolean BUILDING_INTERIOR = LogicUtil.nextBoolean()
	protected static final boolean ESTABLISHMENT = LogicUtil.nextBoolean()
	protected static final boolean MEDICAL_ESTABLISHMENT = LogicUtil.nextBoolean()
	protected static final boolean DS9_ESTABLISHMENT = LogicUtil.nextBoolean()
	protected static final boolean SCHOOL = LogicUtil.nextBoolean()
	protected static final boolean MIRROR = LogicUtil.nextBoolean()
	protected static final boolean ALTERNATE_REALITY = LogicUtil.nextBoolean()

}
