package com.cezarykluczynski.stapi.util.tool

import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

class TimeUtil {

	static XMLGregorianCalendar createXmlGregorianCalendar(int year, int month, int day) {
		XMLGregorianCalendar gregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar()
		gregorianCalendar.setYear(year)
		gregorianCalendar.setMonth(month)
		gregorianCalendar.setDay(day)
		gregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED)
		gregorianCalendar
	}

}
