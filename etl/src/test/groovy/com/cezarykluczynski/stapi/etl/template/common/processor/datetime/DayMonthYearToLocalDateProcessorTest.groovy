package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class DayMonthYearToLocalDateProcessorTest extends Specification {

	private DayMonthYearToLocalDateProcessor dayMonthYearToLocalDateProcessor

	void setup() {
		dayMonthYearToLocalDateProcessor = new DayMonthYearToLocalDateProcessor()
	}

	@Unroll('maps #dayMonthYear to #localDate')
	void "maps DayMonthYear to LocalDate"() {
		expect:
		localDate == dayMonthYearToLocalDateProcessor.process(dayMonthYear)

		where:
		localDate                | dayMonthYear
		null                     | null
		null                     | DayMonthYear.of(null, null, null)
		null                     | DayMonthYear.of(2001, null, null)
		null                     | DayMonthYear.of(2001, 1, null)
		LocalDate.of(2001, 2, 1) | DayMonthYear.of(1, 2, 2001)
	}

}
