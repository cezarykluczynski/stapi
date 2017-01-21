package com.cezarykluczynski.stapi.server.common.mapper

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate
import java.time.Month

class DateMapperTest extends Specification {

	private static final Integer YEAR = 2000
	private static final Integer MONTH = 1
	private static final Integer DAY = 15

	private DateMapper dateMapper

	void setup() {
		dateMapper = Mappers.getMapper(DateMapper)
	}

	void "maps null XMLGregorianCalendar to null LocalDate"() {
		expect:
		dateMapper.map(null) == null
	}

	void "maps XMLGregorianCalendar  LocalDate"() {
		given:
		XMLGregorianCalendar xmlGregorianCalendar = new XMLGregorianCalendarImpl()
		xmlGregorianCalendar.setYear(YEAR)
		xmlGregorianCalendar.setMonth(MONTH)
		xmlGregorianCalendar.setDay(DAY)

		when:
		LocalDate localDate = dateMapper.map xmlGregorianCalendar

		then:
		localDate.year == YEAR
		localDate.month == Month.JANUARY
		localDate.dayOfMonth == DAY
	}

}
