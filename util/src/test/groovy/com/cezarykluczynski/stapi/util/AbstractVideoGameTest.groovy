package com.cezarykluczynski.stapi.util

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl

import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

class AbstractVideoGameTest extends AbstractTest {

	protected static final String UID = 'UID'
	protected static final String TITLE = 'TITLE'
	protected static final Float STARDATE_FROM = 1514.2F
	protected static final Float STARDATE_TO = 1517.5F
	protected static final Integer YEAR_FROM = 2350
	protected static final Integer YEAR_TO = 2390
	protected static final LocalDate RELEASE_DATE = LocalDate.of(1990, 8, 4)
	protected static final LocalDate RELEASE_DATE_FROM = LocalDate.of(1991, 1, 2)
	protected static final LocalDate RELEASE_DATE_TO = LocalDate.of(1993, 3, 4)
	protected static final XMLGregorianCalendar RELEASE_DATE_FROM_XML = XMLGregorianCalendarImpl
			.createDate(1991, 1, 2, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar RELEASE_DATE_TO_XML = XMLGregorianCalendarImpl
			.createDate(1993, 3, 4, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(1990, 8, 4, DatatypeConstants.FIELD_UNDEFINED)
	protected static final String SYSTEM_REQUIREMENTS = 'SYSTEM_REQUIREMENTS'

}
