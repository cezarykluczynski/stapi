package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.LogicUtil
import spock.lang.Specification

abstract class AbstractFoodTest extends Specification {

	protected static final String NAME = 'NAME'
	protected static final String GUID = 'GUID'
	protected static final boolean EARTHLY_ORIGIN = LogicUtil.nextBoolean()
	protected static final boolean DESSERT = LogicUtil.nextBoolean()
	protected static final boolean FRUIT = LogicUtil.nextBoolean()
	protected static final boolean HERB_OR_SPICE = LogicUtil.nextBoolean()
	protected static final boolean SAUCE = LogicUtil.nextBoolean()
	protected static final boolean SOUP = LogicUtil.nextBoolean()
	protected static final boolean BEVERAGE = LogicUtil.nextBoolean()
	protected static final boolean ALCOHOLIC_BEVERAGE = LogicUtil.nextBoolean()
	protected static final boolean JUICE = LogicUtil.nextBoolean()
	protected static final boolean TEA = LogicUtil.nextBoolean()

}
