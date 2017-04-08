package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearCandidate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

import java.time.YearMonth

class MonthlinkTemplateToMonthYearProcessorTest extends Specification {

	private static final Integer YEAR = 2000
	private static final Integer MONTH = 10

	private MonthlinkTemplateToMonthYearCandiateProcessor monthlinkTemplateToDayMonthYearCandiateProcessorMock

	private MonthYearCandidateToYearMonthProcessor monthYearCandidateToYearMonthProcessorMock

	private MonthlinkTemplateToMonthYearProcessor monthlinkTemplateToDayMonthYearProcessor

	void setup() {
		monthlinkTemplateToDayMonthYearCandiateProcessorMock = Mock()
		monthYearCandidateToYearMonthProcessorMock = Mock()
		monthlinkTemplateToDayMonthYearProcessor = new MonthlinkTemplateToMonthYearProcessor(monthlinkTemplateToDayMonthYearCandiateProcessorMock,
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
		1 * monthlinkTemplateToDayMonthYearCandiateProcessorMock.process(template) >> monthYearCandidate
		1 * monthYearCandidateToYearMonthProcessorMock.process(monthYearCandidate) >> yearMonth
		0 * _
		dayMonthYear.year == YEAR
		dayMonthYear.month == MONTH
	}

}
