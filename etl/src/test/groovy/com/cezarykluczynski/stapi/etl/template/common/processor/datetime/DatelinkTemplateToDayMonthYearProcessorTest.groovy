package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

import java.time.LocalDate

class DatelinkTemplateToDayMonthYearProcessorTest extends Specification {

	private static final Integer YEAR = 2000
	private static final Integer MONTH = 10
	private static final Integer DAY = 20

	private DatelinkTemplateToDayMonthYearCandiateProcessor datelinkTemplateToDayMonthYearCandiateProcessorMock

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessorMock

	private DatelinkTemplateToDayMonthYearProcessor datelinkTemplateToDayMonthYearProcessor

	void setup() {
		datelinkTemplateToDayMonthYearCandiateProcessorMock = Mock()
		dayMonthYearCandidateToLocalDateProcessorMock = Mock()
		datelinkTemplateToDayMonthYearProcessor = new DatelinkTemplateToDayMonthYearProcessor(datelinkTemplateToDayMonthYearCandiateProcessorMock,
				dayMonthYearCandidateToLocalDateProcessorMock)
	}

	void "process template using dependencies, then returns DayMonthYear made of LocalDate"() {
		given:
		Template template = Mock()
		LocalDate localDate = LocalDate.of(YEAR, MONTH, DAY)
		DayMonthYearCandidate dayMonthYearCandidate = Mock()

		when:
		DayMonthYear dayMonthYear = datelinkTemplateToDayMonthYearProcessor.process(template)

		then:
		1 * datelinkTemplateToDayMonthYearCandiateProcessorMock.process(template) >> dayMonthYearCandidate
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(dayMonthYearCandidate) >> localDate
		0 * _
		dayMonthYear.year == YEAR
		dayMonthYear.month == MONTH
		dayMonthYear.day == DAY
	}

	void "returns null when DayMonthYearCandidateToLocalDateProcessor returns null"() {
		given:
		Template template = Mock()
		DayMonthYearCandidate dayMonthYearCandidate = Mock()

		when:
		DayMonthYear dayMonthYear = datelinkTemplateToDayMonthYearProcessor.process(template)

		then:
		1 * datelinkTemplateToDayMonthYearCandiateProcessorMock.process(template) >> dayMonthYearCandidate
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(dayMonthYearCandidate) >> null
		0 * _
		dayMonthYear == null
	}

}
