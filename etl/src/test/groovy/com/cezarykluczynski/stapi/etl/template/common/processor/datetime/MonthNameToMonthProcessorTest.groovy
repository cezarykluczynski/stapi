package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import spock.lang.Specification

import java.time.Month

class MonthNameToMonthProcessorTest extends Specification {

	private static final String VALID_MONTH_STRING = 'April'
	private static final Month VALID_MONTH_MONTH = Month.APRIL
	private static final String INVALID_MONTH = 'INVALID_MONTH'

	private MonthNameToMonthProcessor monthNameToMonthProcessor

	void setup() {
		monthNameToMonthProcessor = new MonthNameToMonthProcessor()
	}

	void "processes valid string"() {
		when:
		Month month = monthNameToMonthProcessor.process(VALID_MONTH_STRING)

		then:
		month == VALID_MONTH_MONTH
	}

	void "processes invalid string"() {
		when:
		Month month = monthNameToMonthProcessor.process(INVALID_MONTH)

		then:
		month == null
	}

}
