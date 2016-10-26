package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate
import com.cezarykluczynski.stapi.util.constant.TemplateNames
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
	private static final String YEAR_STRING = YEAR.toString()
	private static final String MONTH_STRING = MONTH.toString()
	private static final String DAY_STRING = DAY.toString()

	private DayMonthYearProcessor dayMonthYearProcessorMock

	private DatelinkTemplateToLocalDateProcessor templateToLocalDateProcessor

	private Template template

	def setup() {
		dayMonthYearProcessorMock = Mock(DayMonthYearProcessor)
		templateToLocalDateProcessor = new DatelinkTemplateToLocalDateProcessor(dayMonthYearProcessorMock)
		template = new Template(
				title: TemplateNames.D,
				parts: Lists.newArrayList(
						new Template.Part(key: TemplateParam.FIRST, value: DAY_STRING),
						new Template.Part(key: TemplateParam.SECOND, value: MONTH_STRING),
						new Template.Part(key: TemplateParam.THIRD, value: YEAR_STRING),
				)
		)
	}

	def "valid template with title 'd' passed findings to DayMonthYearProcessor"() {
		given:
		LocalDate localDate = LocalDate.of(YEAR, MONTH, DAY)

		when:
		LocalDate localDateOutput = templateToLocalDateProcessor.process(template)

		then:
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			dayMonthYearCandidate.day == DAY_STRING
			dayMonthYearCandidate.month == MONTH_STRING
			dayMonthYearCandidate.year == YEAR_STRING
			return localDate
		}
		localDateOutput == localDate
	}

	def "valid template with title 'datelink' passed findings to DayMonthYearProcessor"() {
		given:
		template.title = TemplateNames.DATELINK
		LocalDate localDate = LocalDate.of(YEAR, MONTH, DAY)

		when:
		LocalDate localDateOutput = templateToLocalDateProcessor.process(template)

		then:
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			dayMonthYearCandidate.day == DAY_STRING
			dayMonthYearCandidate.month == MONTH_STRING
			dayMonthYearCandidate.year == YEAR_STRING
			return localDate
		}
		localDateOutput == localDate
	}

	def "template of different title produces null LocalDate"() {
		when:
		LocalDate localDate = templateToLocalDateProcessor.process(new Template(title: "different template"))

		then:
		localDate == null
	}

}
