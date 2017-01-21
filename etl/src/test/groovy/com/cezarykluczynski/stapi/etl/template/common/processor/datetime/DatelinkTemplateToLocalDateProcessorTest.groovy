package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class DatelinkTemplateToLocalDateProcessorTest extends Specification {

	private static final Integer YEAR = 2000
	private static final Month MONTH = Month.APRIL
	private static final Integer DAY = 3
	private static final String YEAR_STRING = YEAR
	private static final String MONTH_STRING = MONTH
	private static final String DAY_STRING = DAY

	private DayMonthYearProcessor dayMonthYearProcessorMock

	private DatelinkTemplateToLocalDateProcessor templateToLocalDateProcessor

	private Template template

	void setup() {
		dayMonthYearProcessorMock = Mock(DayMonthYearProcessor)
		templateToLocalDateProcessor = new DatelinkTemplateToLocalDateProcessor(dayMonthYearProcessorMock)
		template = new Template(
				title: TemplateName.D,
				parts: Lists.newArrayList(
						new Template.Part(key: TemplateParam.FIRST, value: DAY_STRING),
						new Template.Part(key: TemplateParam.SECOND, value: MONTH_STRING),
						new Template.Part(key: TemplateParam.THIRD, value: YEAR_STRING),
				)
		)
	}

	void "valid template with title 'd' passed findings to DayMonthYearProcessor"() {
		given:
		LocalDate localDate = LocalDate.of(YEAR, MONTH, DAY)

		when:
		LocalDate localDateOutput = templateToLocalDateProcessor.process(template)

		then:
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			dayMonthYearCandidate.day == DAY_STRING
			dayMonthYearCandidate.month == MONTH_STRING
			dayMonthYearCandidate.year == YEAR_STRING
			localDate
		}
		localDateOutput == localDate
	}

	void "valid template with title 'datelink' passed findings to DayMonthYearProcessor"() {
		given:
		template.title = TemplateName.DATELINK
		LocalDate localDate = LocalDate.of(YEAR, MONTH, DAY)

		when:
		LocalDate localDateOutput = templateToLocalDateProcessor.process(template)

		then:
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			dayMonthYearCandidate.day == DAY_STRING
			dayMonthYearCandidate.month == MONTH_STRING
			dayMonthYearCandidate.year == YEAR_STRING
			localDate
		}
		localDateOutput == localDate
	}

	void "template of different title produces null LocalDate"() {
		when:
		LocalDate localDate = templateToLocalDateProcessor.process(new Template(title: 'different template'))

		then:
		localDate == null
	}

}
