package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractBookSeriesTest extends AbstractTest {

	protected static final String UID = 'ABCD0987654321'
	protected static final String TITLE = 'TITLE'
	protected static final Integer PUBLISHED_YEAR_FROM = 1990
	protected static final Integer PUBLISHED_MONTH_FROM = 10
	protected static final Integer PUBLISHED_YEAR_TO = 1994
	protected static final Integer PUBLISHED_MONTH_TO = 6
	protected static final Integer NUMBER_OF_BOOKS = 9
	protected static final Integer NUMBER_OF_BOOKS_FROM = 13
	protected static final Integer NUMBER_OF_BOOKS_TO = 15
	protected static final Integer YEAR_FROM = 2350
	protected static final Integer YEAR_TO = 2351
	protected static final Boolean MINISERIES = RandomUtil.nextBoolean()
	protected static final Boolean E_BOOK_SERIES = RandomUtil.nextBoolean()

}
