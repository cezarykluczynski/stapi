package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

abstract class AbstractComicsTest extends AbstractTest {

	protected static final String UID = 'ABCD0987654321'
	protected static final String TITLE = 'TITLE'
	protected static final Integer PUBLISHED_YEAR = 1990
	protected static final Integer PUBLISHED_YEAR_FROM = 1989
	protected static final Integer PUBLISHED_YEAR_TO = 1991
	protected static final Integer PUBLISHED_MONTH = 12
	protected static final Integer PUBLISHED_DAY = 31
	protected static final Integer COVER_YEAR = 1991
	protected static final Integer COVER_MONTH = 1
	protected static final Integer COVER_DAY = 2
	protected static final Integer NUMBER_OF_PAGES = 32
	protected static final Integer NUMBER_OF_PAGES_FROM = 16
	protected static final Integer NUMBER_OF_PAGES_TO = 48
	protected static final Float STARDATE_FROM = 12093.5F
	protected static final Float STARDATE_TO = 12321.4F
	protected static final Integer YEAR_FROM = 2350
	protected static final Integer YEAR_TO = 2351
	protected static final boolean PHOTONOVEL = RandomUtil.nextBoolean()
	protected static final boolean ADAPTATION = RandomUtil.nextBoolean()

}
