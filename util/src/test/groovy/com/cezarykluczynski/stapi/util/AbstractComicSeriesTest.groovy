package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

abstract class AbstractComicSeriesTest extends AbstractTest {

	protected static final String UID = 'ABCD0987654321'
	protected static final String TITLE = 'TITLE'
	protected static final Integer PUBLISHED_YEAR_FROM = 1990
	protected static final Integer PUBLISHED_MONTH_FROM = 10
	protected static final Integer PUBLISHED_DAY_FROM = 4
	protected static final Integer PUBLISHED_YEAR_TO = 1994
	protected static final Integer PUBLISHED_MONTH_TO = 6
	protected static final Integer PUBLISHED_DAY_TO = 17
	protected static final Integer NUMBER_OF_ISSUES = 9
	protected static final Integer NUMBER_OF_ISSUES_FROM = 13
	protected static final Integer NUMBER_OF_ISSUES_TO = 15
	protected static final Float STARDATE_FROM = 12093.5F
	protected static final Float STARDATE_TO = 12321.4F
	protected static final Integer YEAR_FROM = 2350
	protected static final Integer YEAR_TO = 2351
	protected static final Boolean MINISERIES = RandomUtil.nextBoolean()
	protected static final Boolean PHOTONOVEL_SERIES = RandomUtil.nextBoolean()

}
