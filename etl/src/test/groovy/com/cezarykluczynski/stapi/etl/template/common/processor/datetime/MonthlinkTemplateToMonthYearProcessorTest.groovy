package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearCandidate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

import java.time.YearMonth

class MonthlinkTemplateToMonthYearProcessorTest extends Specification {

	private static final Integer YEAR = 2000
	private static final Integer MONTH = 10

	private MonthlinkTemplateToMonthYearCandiateProcessor monthlinkTemplateToDayMonthYearCandidateProcessorMock

	private MonthYearCandidateToYearMonthProcessor monthYearCandidateToYearMonthProcessorMock

	private MonthlinkTemplateToMonthYearProcessor monthlinkTemplateToDayMonthYearProcessor

	void setup() {
		monthlinkTemplateToDayMonthYearCandidateProcessorMock = Mock()
		monthYearCandidateToYearMonthProcessorMock = Mock()
		monthlinkTemplateToDayMonthYearProcessor = new MonthlinkTemplateToMonthYearProcessor(monthlinkTemplateToDayMonthYearCandidateProcessorMock,
				monthYearCandidateToYearMonthProcessorMock)
	}

	void "process template using dependencies, then returns DayMonthYear made of LocalDate"() {
		given:
		Template template = Mock()
		YearMonth yearMonth = YearMonth.of(YEAR, MONTH)
		MonthYearCandidate monthYearCandidate = Mock()

		when:
		DayMonthYear dayMonthYear = monthlinkTemplateToDayMonthYearProcessor.process(template)

		then:
		1 * monthlinkTemplateToDayMonthYearCandidateProcessorMock.process(template) >> monthYearCandidate
		1 * monthYearCandidateToYearMonthProcessorMock.process(monthYearCandidate) >> yearMonth
		0 * _
		dayMonthYear.year == YEAR
		dayMonthYear.month == MONTH
	}

	void "returns null when MonthlinkTemplateToDayMonthYearCandidateProcessor returns null"() {
		given:
		Template template = Mock()

		when:
		DayMonthYear dayMonthYear = monthlinkTemplateToDayMonthYearProcessor.process(template)

		then:
		1 * monthlinkTemplateToDayMonthYearCandidateProcessorMock.process(template) >> null
		0 * _
		dayMonthYear == null
	}

	void "returns null when MonthYearCandidateToYearMonthProcessor returns null"() {
		given:
		Template template = Mock()
		MonthYearCandidate monthYearCandidate = Mock()

		when:
		DayMonthYear dayMonthYear = monthlinkTemplateToDayMonthYearProcessor.process(template)

		then:
		1 * monthlinkTemplateToDayMonthYearCandidateProcessorMock.process(template) >> monthYearCandidate
		1 * monthYearCandidateToYearMonthProcessorMock.process(monthYearCandidate) >> null
		0 * _
		dayMonthYear == null
	}

}
