package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearCandidate
import spock.lang.Specification

import java.time.Month
import java.time.YearMonth

class MonthYearCandidateToYearMonthProcessorTest extends Specification {

	private static final String MONTH_STRING = 'April'
	private static final String YEAR_STRING = '1920'
	private static final Month MONTH = Month.APRIL
	private static final Integer YEAR_INTEGER = 1920
	private static final String INVALID_YEAR = 'INVALID_YEAR'

	private MonthNameToMonthProcessor monthNameToMonthProcessorMock

	private MonthYearCandidateToYearMonthProcessor monthYearCandidateToYearMonthProcessor

	void setup() {
		monthNameToMonthProcessorMock = Mock()
		monthYearCandidateToYearMonthProcessor = new MonthYearCandidateToYearMonthProcessor(monthNameToMonthProcessorMock)
	}

	void "returns valid YearMonth"() {
		given:
		MonthYearCandidate monthYearCandidate = MonthYearCandidate.of(MONTH_STRING, YEAR_STRING)

		when:
		YearMonth yearMonth = monthYearCandidateToYearMonthProcessor.process(monthYearCandidate)

		then:
		1 * monthNameToMonthProcessorMock.process(MONTH_STRING) >> MONTH
		yearMonth.year == YEAR_INTEGER
		yearMonth.monthValue == MONTH.value
	}

	void "returns null when year is invalid"() {
		given:
		MonthYearCandidate monthYearCandidate = MonthYearCandidate.of(MONTH_STRING, INVALID_YEAR)

		when:
		YearMonth yearMonth = monthYearCandidateToYearMonthProcessor.process(monthYearCandidate)

		then:
		0 * _
		yearMonth == null
	}

	void "returns null when month is null"() {
		given:
		MonthYearCandidate monthYearCandidate = MonthYearCandidate.of(MONTH_STRING, YEAR_STRING)

		when:
		YearMonth yearMonth = monthYearCandidateToYearMonthProcessor.process(monthYearCandidate)

		then:
		1 * monthNameToMonthProcessorMock.process(MONTH_STRING) >> null
		yearMonth == null
	}

}
