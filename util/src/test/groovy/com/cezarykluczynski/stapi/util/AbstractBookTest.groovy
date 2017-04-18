package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.LogicUtil
import spock.lang.Specification

abstract class AbstractBookTest extends Specification {

	protected static final String GUID = 'ABCD0987654321'
	protected static final String TITLE = 'TITLE'
	protected static final String PRODUCTION_NUMBER = 'PRODUCTION_NUMBER'
	protected static final Integer PUBLISHED_YEAR = 1990
	protected static final Integer PUBLISHED_YEAR_FROM = 1989
	protected static final Integer PUBLISHED_YEAR_TO = 1991
	protected static final Integer PUBLISHED_MONTH = 12
	protected static final Integer PUBLISHED_DAY = 31
	protected static final Integer AUDIOBOOK_PUBLISHED_YEAR = 1980
	protected static final Integer AUDIOBOOK_PUBLISHED_YEAR_FROM = 1979
	protected static final Integer AUDIOBOOK_PUBLISHED_YEAR_TO = 1981
	protected static final Integer AUDIOBOOK_PUBLISHED_MONTH = 11
	protected static final Integer AUDIOBOOK_PUBLISHED_DAY = 30
	protected static final Integer AUDIOBOOK_RUN_TIME = 90
	protected static final Integer NUMBER_OF_PAGES = 32
	protected static final Integer NUMBER_OF_PAGES_FROM = 16
	protected static final Integer NUMBER_OF_PAGES_TO = 48
	protected static final Float STARDATE_FROM = 12093.5F
	protected static final Float STARDATE_TO = 12321.4F
	protected static final Integer YEAR_FROM = 2350
	protected static final Integer YEAR_TO = 2351
	protected static final boolean NOVEL = LogicUtil.nextBoolean()
	protected static final boolean REFERENCE_BOOK = LogicUtil.nextBoolean()
	protected static final boolean BIOGRAPHY_BOOK = LogicUtil.nextBoolean()
	protected static final boolean ROLE_PLAYING_BOOK = LogicUtil.nextBoolean()
	protected static final boolean E_BOOK = LogicUtil.nextBoolean()
	protected static final boolean ANTHOLOGY = LogicUtil.nextBoolean()
	protected static final boolean NOVELIZATION = LogicUtil.nextBoolean()
	protected static final boolean AUDIOBOOK = LogicUtil.nextBoolean()
	protected static final boolean AUDIOBOOK_ABRIDGED = LogicUtil.nextBoolean()

}
