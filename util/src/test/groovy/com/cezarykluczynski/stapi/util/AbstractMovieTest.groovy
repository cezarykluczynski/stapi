package com.cezarykluczynski.stapi.util

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl

import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

abstract class AbstractMovieTest extends AbstractTest {

	protected static final String UID = 'UID'
	protected static final String TITLE = 'TITLE'
	protected static final String TITLE_BULGARIAN = 'TITLE_BULGARIAN'
	protected static final String TITLE_CATALAN = 'TITLE_CATALAN'
	protected static final String TITLE_CHINESE_TRADITIONAL = 'TITLE_CHINESE_TRADITIONAL'
	protected static final String TITLE_GERMAN = 'TITLE_GERMAN'
	protected static final String TITLE_ITALIAN = 'TITLE_ITALIAN'
	protected static final String TITLE_JAPANESE = 'TITLE_JAPANESE'
	protected static final String TITLE_POLISH = 'TITLE_POLISH'
	protected static final String TITLE_RUSSIAN = 'TITLE_RUSSIAN'
	protected static final String TITLE_SERBIAN = 'TITLE_SERBIAN'
	protected static final String TITLE_SPANISH = 'TITLE_SPANISH'
	protected static final Float STARDATE_FROM = 1514.2F
	protected static final Float STARDATE_TO = 1517.5F
	protected static final Integer YEAR_FROM = 2350
	protected static final Integer YEAR_TO = 2390
	protected static final LocalDate US_RELEASE_DATE = LocalDate.of(1990, 8, 4)
	protected static final LocalDate US_RELEASE_DATE_FROM = LocalDate.of(1991, 1, 2)
	protected static final LocalDate US_RELEASE_DATE_TO = LocalDate.of(1993, 3, 4)
	protected static final XMLGregorianCalendar US_RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(1990, 8, 4, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar US_RELEASE_DATE_FROM_XML = XMLGregorianCalendarImpl
			.createDate(1991, 1, 2, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar US_RELEASE_DATE_TO_XML = XMLGregorianCalendarImpl
			.createDate(1993, 3, 4, DatatypeConstants.FIELD_UNDEFINED)

}
