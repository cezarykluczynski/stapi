package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.TimeUtil

import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

abstract class AbstractSeriesTest extends AbstractTest {

	protected static final String UID = 'UID'
	protected static final String TITLE = 'TITLE'
	protected static final String ABBREVIATION = 'ABBREVIATION'
	protected static final Integer PRODUCTION_START_YEAR = 1998
	protected static final Integer PRODUCTION_END_YEAR = 2002
	protected static final Integer PRODUCTION_START_YEAR_FROM = 1990
	protected static final Integer PRODUCTION_START_YEAR_TO = 1992
	protected static final Integer PRODUCTION_END_YEAR_FROM = 1997
	protected static final Integer PRODUCTION_END_YEAR_TO = 1999
	protected static final Integer SEASONS_COUNT = 1
	protected static final Integer EPISODES_COUNT = 2
	protected static final Integer FEATURE_LENGTH_EPISODES_COUNT = 3
	protected static final LocalDate ORIGINAL_RUN_START_DATE = LocalDate.of(1999, 5, 3)
	protected static final LocalDate ORIGINAL_RUN_END_DATE = LocalDate.of(2001, 2, 18)
	protected static final LocalDate ORIGINAL_RUN_START_DATE_FROM_DB = LocalDate.of(1991, 1, 2)
	protected static final LocalDate ORIGINAL_RUN_START_DATE_TO_DB = LocalDate.of(1993, 3, 4)
	protected static final LocalDate ORIGINAL_RUN_END_DATE_FROM_DB = LocalDate.of(1996, 5, 6)
	protected static final LocalDate ORIGINAL_RUN_END_DATE_TO_DB = LocalDate.of(1998, 7, 8)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_START_DATE_FROM_XML = TimeUtil.createXmlGregorianCalendar(1991, 1, 2)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_START_DATE_TO_XML = TimeUtil.createXmlGregorianCalendar(1993, 3, 4)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_END_DATE_FROM_XML = TimeUtil.createXmlGregorianCalendar(1996, 5, 6)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_END_DATE_TO_XML = TimeUtil.createXmlGregorianCalendar(1998, 7, 8)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_START_DATE_XML = TimeUtil.createXmlGregorianCalendar(1999, 5, 3)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_END_DATE_XML = TimeUtil.createXmlGregorianCalendar(2001, 2, 18)

}
