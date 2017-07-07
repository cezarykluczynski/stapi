package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl

import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

class AbstractVideoReleaseTest extends AbstractTest {

	protected static final String UID = 'ABCD0123456789'
	protected static final String TITLE = 'TITLE'
	protected static final Integer NUMBER_OF_EPISODES = 26
	protected static final Integer NUMBER_OF_FEATURE_LENGTH_EPISODES = 3
	protected static final Integer NUMBER_OF_DATA_CARRIERS = 4
	protected static final Integer RUN_TIME = 907
	protected static final Integer RUN_TIME_FROM = 502
	protected static final Integer RUN_TIME_TO = 1121
	protected static final Integer YEAR_FROM = 2365
	protected static final Integer YEAR_TO = 2370
	protected static final LocalDate REGION_FREE_RELEASE_DATE = LocalDate.of(2001, 01, 01)
	protected static final LocalDate REGION1_A_RELEASE_DATE = LocalDate.of(2002, 02, 02)
	protected static final LocalDate REGION1_SLIMLINE_RELEASE_DATE = LocalDate.of(2003, 03, 03)
	protected static final LocalDate REGION2_B_RELEASE_DATE = LocalDate.of(2004, 04, 04)
	protected static final LocalDate REGION2_SLIMLINE_RELEASE_DATE = LocalDate.of(2005, 05, 05)
	protected static final LocalDate REGION4_A_RELEASE_DATE = LocalDate.of(2006, 06, 06)
	protected static final LocalDate REGION4_SLIMLINE_RELEASE_DATE = LocalDate.of(2007, 07, 07)
	protected static final XMLGregorianCalendar REGION_FREE_RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(2001, 01, 01, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar REGION1_A_RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(2002, 02, 02, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar REGION1_SLIMLINE_RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(2003, 03, 03, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar REGION2_B_RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(2004, 04, 04, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar REGION2_SLIMLINE_RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(2005, 05, 05, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar REGION4_A_RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(2006, 06, 06, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar REGION4_SLIMLINE_RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(2007, 07, 07, DatatypeConstants.FIELD_UNDEFINED)
	protected static final boolean AMAZON_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean DAILYMOTION_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean GOOGLE_PLAY_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean I_TUNES_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean ULTRA_VIOLET_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean VIMEO_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean VUDU_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean XBOX_SMART_GLASS_DIGITAL = RandomUtil.nextBoolean()
	protected static final boolean YOU_TUBE_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean NETFLIX_DIGITAL_RELEASE = RandomUtil.nextBoolean()

}
