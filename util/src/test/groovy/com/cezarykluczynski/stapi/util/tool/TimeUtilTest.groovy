package com.cezarykluczynski.stapi.util.tool

import spock.lang.Specification

import javax.xml.datatype.XMLGregorianCalendar

class TimeUtilTest extends Specification {

	void "creates XMLGregorianCalendar"() {
		given:
		XMLGregorianCalendar xmlGregorianCalendar = TimeUtil.createXmlGregorianCalendar(2022, 12, 31)

		expect:
		xmlGregorianCalendar.year == 2022
		xmlGregorianCalendar.month == 12
		xmlGregorianCalendar.day == 31
	}

}
