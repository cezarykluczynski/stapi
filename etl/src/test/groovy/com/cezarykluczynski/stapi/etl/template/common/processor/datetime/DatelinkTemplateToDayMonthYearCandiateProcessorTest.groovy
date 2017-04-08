package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.Month

class DatelinkTemplateToDayMonthYearCandiateProcessorTest extends Specification {

	private static final Integer YEAR = 2000
	private static final Month MONTH = Month.APRIL
	private static final Integer DAY = 3
	private static final String YEAR_STRING = YEAR
	private static final String MONTH_STRING = MONTH
	private static final String DAY_STRING = DAY

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessorMock

	private DatelinkTemplateToDayMonthYearCandiateProcessor datelinkTemplateToDayMonthYearCandiateProcessor

	private Template template

	void setup() {
		dayMonthYearCandidateToLocalDateProcessorMock = Mock()
		datelinkTemplateToDayMonthYearCandiateProcessor = new DatelinkTemplateToDayMonthYearCandiateProcessor()
		template = new Template(
				title: TemplateTitle.D,
				parts: Lists.newArrayList(
						new Template.Part(key: TemplateParam.FIRST, value: DAY_STRING),
						new Template.Part(key: TemplateParam.SECOND, value: MONTH_STRING),
						new Template.Part(key: TemplateParam.THIRD, value: YEAR_STRING),
				)
		)
	}

	void "valid template with title 'd' is parsed"() {
		when:
		DayMonthYearCandidate dayMonthYearCandidate = datelinkTemplateToDayMonthYearCandiateProcessor.process(template)

		then:
		dayMonthYearCandidate.day == DAY_STRING
		dayMonthYearCandidate.month == MONTH_STRING
		dayMonthYearCandidate.year == YEAR_STRING
	}

	void "valid template with title 'datelink' passed findings to DayMonthYearProcessor"() {
		given:
		template.title = TemplateTitle.DATELINK

		when:
		DayMonthYearCandidate dayMonthYearCandidate = datelinkTemplateToDayMonthYearCandiateProcessor.process(template)

		then:
		dayMonthYearCandidate.day == DAY_STRING
		dayMonthYearCandidate.month == MONTH_STRING
		dayMonthYearCandidate.year == YEAR_STRING
	}

	void "template of different title produces null LocalDate"() {
		when:
		DayMonthYearCandidate dayMonthYearCandidate = datelinkTemplateToDayMonthYearCandiateProcessor
				.process(new Template(title: 'different template'))

		then:
		dayMonthYearCandidate == null
	}

}
