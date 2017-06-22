package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class DatePartToLocalDateProcessorTest extends Specification {

	private DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessorMock

	private DayMonthYearToLocalDateProcessor dayMonthYearToLocalDateProcessorMock

	private DatePartToLocalDateProcessor datePartToLocalDateProcessor

	void setup() {
		datePartToDayMonthYearProcessorMock = Mock()
		dayMonthYearToLocalDateProcessorMock = Mock()
		datePartToLocalDateProcessor = new DatePartToLocalDateProcessor(datePartToDayMonthYearProcessorMock, dayMonthYearToLocalDateProcessorMock)
	}

	void "passes result of DatePartToDayMonthYearProcessor to DayMonthYearToLocalDateProcessor, then returns result"() {
		given:
		Template.Part templatePart = Mock()
		DayMonthYear dayMonthYear = Mock()
		LocalDate localDate = LocalDate.of(2001, Month.FEBRUARY, 3)

		when:
		LocalDate localDateOutput = datePartToLocalDateProcessor.process(templatePart)

		then:
		1 * datePartToDayMonthYearProcessorMock.process(templatePart) >> dayMonthYear
		1 * dayMonthYearToLocalDateProcessorMock.process(dayMonthYear) >> localDate
		0 * _
		localDateOutput == localDate
	}

}
