package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class DayMonthYearCandidateToLocalDateProcessorTest extends Specification {

	private static final String VALID_DAY = '3'
	private static final String VALID_MONTH_STRING = 'August'
	private static final Month VALID_MONTH_MONTH = Month.AUGUST
	private static final String VALID_YEAR = '1998'
	private static final String INVALID = 'INVALID'
	private static final String QUESTION_MARK = '?'

	private MonthNameToMonthProcessor monthNameToMonthProcessorMock

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor

	void setup() {
		monthNameToMonthProcessorMock = Mock()
		dayMonthYearCandidateToLocalDateProcessor = new DayMonthYearCandidateToLocalDateProcessor(monthNameToMonthProcessorMock)
	}

	void "valid set of data is parsed to LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(VALID_DAY, VALID_MONTH_STRING, VALID_YEAR))

		then:
		1 * monthNameToMonthProcessorMock.process(VALID_MONTH_STRING) >> VALID_MONTH_MONTH
		localDate.dayOfMonth == 3
		localDate.month == Month.AUGUST
		localDate.year == 1998
	}

	void "returns null when any of part is a question mark"() {
		expect:
		dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(QUESTION_MARK, VALID_MONTH_STRING, VALID_YEAR)) == null
		dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(VALID_DAY, QUESTION_MARK, VALID_YEAR)) == null
		dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(VALID_DAY, VALID_MONTH_STRING, QUESTION_MARK)) == null
	}

	void "null day results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(null, VALID_MONTH_STRING, VALID_YEAR))

		then:
		localDate == null
	}

	void "invalid day results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(INVALID, VALID_MONTH_STRING, VALID_YEAR))

		then:
		localDate == null
	}

	void "null month results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(VALID_DAY, null, VALID_YEAR))

		then:
		localDate == null
	}

	void "invalid month results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(INVALID, INVALID, VALID_YEAR))

		then:
		localDate == null
	}

	void "null year results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(VALID_DAY, VALID_MONTH_STRING, null))

		then:
		localDate == null
	}

	void "invalid year results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(INVALID, VALID_MONTH_STRING, INVALID))

		then:
		localDate == null
	}

}
