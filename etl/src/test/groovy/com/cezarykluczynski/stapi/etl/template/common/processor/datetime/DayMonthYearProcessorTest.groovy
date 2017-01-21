package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class DayMonthYearProcessorTest extends Specification {

	private static final String VALID_DAY = '3'
	private static final String VALID_MONTH = 'August'
	private static final String VALID_YEAR = '1998'
	private static final String INVALID = 'INVALID'
	private static final String QUESTION_MARK = '?'

	private DayMonthYearProcessor dayMonthYearProcessor

	void setup() {
		dayMonthYearProcessor = new DayMonthYearProcessor()
	}

	void "valid set of data is parsed to LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearProcessor.process(DayMonthYearCandidate.of(VALID_DAY, VALID_MONTH, VALID_YEAR))

		then:
		localDate.dayOfMonth == 3
		localDate.month == Month.AUGUST
		localDate.year == 1998
	}

	void "returns null when any of part is a question mark"() {
		expect:
		dayMonthYearProcessor.process(DayMonthYearCandidate.of(QUESTION_MARK, VALID_MONTH, VALID_YEAR)) == null
		dayMonthYearProcessor.process(DayMonthYearCandidate.of(VALID_DAY, QUESTION_MARK, VALID_YEAR)) == null
		dayMonthYearProcessor.process(DayMonthYearCandidate.of(VALID_DAY, VALID_MONTH, QUESTION_MARK)) == null
	}

	void "null day results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearProcessor.process(DayMonthYearCandidate.of(null, VALID_MONTH, VALID_YEAR))

		then:
		localDate == null
	}

	void "invalid day results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearProcessor.process(DayMonthYearCandidate.of(INVALID, VALID_MONTH, VALID_YEAR))

		then:
		localDate == null
	}

	void "null month results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearProcessor.process(DayMonthYearCandidate.of(VALID_DAY, null, VALID_YEAR))

		then:
		localDate == null
	}

	void "invalid month results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearProcessor.process(DayMonthYearCandidate.of(INVALID, INVALID, VALID_YEAR))

		then:
		localDate == null
	}

	void "null year results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearProcessor.process(DayMonthYearCandidate.of(VALID_DAY, VALID_MONTH, null))

		then:
		localDate == null
	}

	void "invalid year results in null LocalDate"() {
		when:
		LocalDate localDate = dayMonthYearProcessor.process(DayMonthYearCandidate.of(INVALID, VALID_MONTH, INVALID))

		then:
		localDate == null
	}

}
