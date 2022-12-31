package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.cezarykluczynski.stapi.util.tool.TimeUtil

import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

abstract class AbstractEpisodeTest extends AbstractTest {

	protected static final String UID = 'UID'
	protected static final String TITLE = 'TITLE'
	protected static final String TITLE_GERMAN = 'TITLE_GERMAN'
	protected static final String TITLE_ITALIAN = 'TITLE_ITALIAN'
	protected static final String TITLE_JAPANESE = 'TITLE_JAPANESE'
	protected static final Integer SEASON_NUMBER = 1
	protected static final Integer SEASON_NUMBER_FROM = 2
	protected static final Integer SEASON_NUMBER_TO = 3
	protected static final Integer EPISODE_NUMBER = 4
	protected static final Integer EPISODE_NUMBER_FROM = 5
	protected static final Integer EPISODE_NUMBER_TO = 6
	protected static final String PRODUCTION_SERIAL_NUMBER = 'PRODUCTION_SERIAL_NUMBER'
	protected static final Boolean FEATURE_LENGTH = RandomUtil.nextBoolean()
	protected static final Float STARDATE_FROM = 1514.2F
	protected static final Float STARDATE_TO = 1517.5F
	protected static final Integer YEAR_FROM = 2350
	protected static final Integer YEAR_TO = 2390
	protected static final LocalDate US_AIR_DATE = LocalDate.of(1990, 8, 4)
	protected static final LocalDate US_AIR_DATE_FROM = LocalDate.of(1991, 1, 2)
	protected static final LocalDate US_AIR_DATE_TO = LocalDate.of(1993, 3, 4)
	protected static final LocalDate FINAL_SCRIPT_DATE = LocalDate.of(1989, 4, 2)
	protected static final LocalDate FINAL_SCRIPT_DATE_FROM = LocalDate.of(1996, 5, 6)
	protected static final LocalDate FINAL_SCRIPT_DATE_TO = LocalDate.of(1998, 7, 8)
	protected static final XMLGregorianCalendar US_AIR_DATE_XML = TimeUtil.createXmlGregorianCalendar(1990, 8, 4)
	protected static final XMLGregorianCalendar US_AIR_DATE_FROM_XML = TimeUtil.createXmlGregorianCalendar(1991, 1, 2)
	protected static final XMLGregorianCalendar US_AIR_DATE_TO_XML = TimeUtil.createXmlGregorianCalendar(1993, 3, 4)
	protected static final XMLGregorianCalendar FINAL_SCRIPT_DATE_XML = TimeUtil.createXmlGregorianCalendar(1989, 4, 2)
	protected static final XMLGregorianCalendar FINAL_SCRIPT_DATE_FROM_XML = TimeUtil.createXmlGregorianCalendar(1996, 5, 6)
	protected static final XMLGregorianCalendar FINAL_SCRIPT_DATE_TO_XML = TimeUtil.createXmlGregorianCalendar(1998, 7, 8)

}
