package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

abstract class AbstractFoodTest extends AbstractTest {

	protected static final String NAME = 'NAME'
	protected static final String UID = 'UID'
	protected static final boolean EARTHLY_ORIGIN = RandomUtil.nextBoolean()
	protected static final boolean DESSERT = RandomUtil.nextBoolean()
	protected static final boolean FRUIT = RandomUtil.nextBoolean()
	protected static final boolean HERB_OR_SPICE = RandomUtil.nextBoolean()
	protected static final boolean SAUCE = RandomUtil.nextBoolean()
	protected static final boolean SOUP = RandomUtil.nextBoolean()
	protected static final boolean BEVERAGE = RandomUtil.nextBoolean()
	protected static final boolean ALCOHOLIC_BEVERAGE = RandomUtil.nextBoolean()
	protected static final boolean JUICE = RandomUtil.nextBoolean()
	protected static final boolean TEA = RandomUtil.nextBoolean()

}
