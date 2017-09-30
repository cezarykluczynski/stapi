package com.cezarykluczynski.stapi.etl.common.service.step

import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.google.common.collect.Maps
import spock.lang.Specification

import java.time.LocalDateTime

class StepTimeFormatterTest extends Specification {

	private static final LocalDateTime START_TIME_1 = LocalDateTime.of(2017, 9, 30, 7, 30, 20)
	private static final LocalDateTime END_TIME_1 =   LocalDateTime.of(2017, 10, 1, 8, 29, 40)
	private static final LocalDateTime START_TIME_2 = LocalDateTime.of(2017, 10, 1, 8, 33, 30)
	private static final LocalDateTime END_TIME_2 =   LocalDateTime.of(2017, 10, 1, 12, 14, 52)
	private static final String FORMATTED_STEPS_TITAL_TIME = """
Steps run times:
Step ${StepName.CREATE_TITLES}    lasted for 01 d 00 h 59 m 20 s (between 30-09-2017 07:30:20 and 01-10-2017 08:29:40)
Step ${StepName.CREATE_MATERIALS} lasted for      03 h 41 m 22 s (between 01-10-2017 08:33:30 and 01-10-2017 12:14:52)
"""

	private StepTimeFormatter stepTimeFormatter

	void setup() {
		stepTimeFormatter = new StepTimeFormatter()
	}

	void "should format steps total times"() {
		given:
		Map<String, Range<LocalDateTime>> stepsTotalTimes = Maps.newLinkedHashMap()
		stepsTotalTimes.put(StepName.CREATE_TITLES, Range.of(START_TIME_1, END_TIME_1))
		stepsTotalTimes.put(StepName.CREATE_MATERIALS, Range.of(START_TIME_2, END_TIME_2))

		expect:
		stepTimeFormatter.format(stepsTotalTimes) == FORMATTED_STEPS_TITAL_TIME
	}

}
