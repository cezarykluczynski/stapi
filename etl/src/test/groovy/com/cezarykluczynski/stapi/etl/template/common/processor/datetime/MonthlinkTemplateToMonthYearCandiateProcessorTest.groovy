package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearCandidate
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.Month

class MonthlinkTemplateToMonthYearCandiateProcessorTest extends Specification {

	private static final Integer YEAR = 2000
	private static final Month MONTH = Month.APRIL
	private static final String YEAR_STRING = YEAR
	private static final String MONTH_STRING = MONTH

	private MonthYearCandidateToYearMonthProcessor monthYearCandidateToYearMonthProcessorMock

	private MonthlinkTemplateToMonthYearCandiateProcessor monthlinkTemplateToMonthYearCandiateProcessor

	private Template template

	void setup() {
		monthYearCandidateToYearMonthProcessorMock = Mock()
		monthlinkTemplateToMonthYearCandiateProcessor = new MonthlinkTemplateToMonthYearCandiateProcessor()
		template = new Template(
				title: TemplateTitle.M,
				parts: Lists.newArrayList(
						new Template.Part(key: TemplateParam.FIRST, value: MONTH_STRING),
						new Template.Part(key: TemplateParam.SECOND, value: YEAR_STRING),
				)
		)
	}

	void "valid template with title 'm' is parsed"() {
		when:
		MonthYearCandidate monthYearCandidate = monthlinkTemplateToMonthYearCandiateProcessor.process(template)

		then:
		monthYearCandidate.month == MONTH_STRING
		monthYearCandidate.year == YEAR_STRING
	}

	void "valid template with title 'monthlink' passed findings to DayMonthYearProcessor"() {
		given:
		template.title = TemplateTitle.MONTHLINK

		when:
		MonthYearCandidate monthYearCandidate = monthlinkTemplateToMonthYearCandiateProcessor.process(template)

		then:
		monthYearCandidate.month == MONTH_STRING
		monthYearCandidate.year == YEAR_STRING
	}

	void "template of different title produces null LocalDate"() {
		when:
		MonthYearCandidate monthYearCandidate = monthlinkTemplateToMonthYearCandiateProcessor.process(new Template(title: 'different template'))

		then:
		monthYearCandidate == null
	}

}
