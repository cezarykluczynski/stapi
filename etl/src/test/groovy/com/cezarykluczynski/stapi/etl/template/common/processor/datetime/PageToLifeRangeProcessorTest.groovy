package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate
import com.cezarykluczynski.stapi.util.constants.TemplateNames
import com.cezarykluczynski.stapi.util.constants.TemplateParam
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.cezarykluczynski.stapi.wiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class PageToLifeRangeProcessorTest extends Specification {

	private static final Integer YEAR_START = 1960
	private static final Month MONTH_START = Month.MAY
	private static final Integer DAY_START = 5
	private static final Integer YEAR_END = 2020
	private static final Month MONTH_END = Month.SEPTEMBER
	private static final Integer DAY_END = 15
	private static final String YEAR_START_STRING = YEAR_START.toString()
	private static final String MONTH_START_STRING = MONTH_START.toString()
	private static final String DAY_START_STRING = DAY_START.toString()
	private static final String YEAR_END_STRING = YEAR_END.toString()
	private static final String MONTH_END_STRING = MONTH_END.toString()
	private static final String DAY_END_STRING = DAY_END.toString()

	private DayMonthYearProcessor dayMonthYearProcessorMock

	private PageToLifeRangeProcessor pageToLifeRangeProcessor

	private Template templateValid

	private Template templateInvalid

	def setup() {
		dayMonthYearProcessorMock = Mock(DayMonthYearProcessor)
		pageToLifeRangeProcessor = new PageToLifeRangeProcessor(dayMonthYearProcessorMock)

		templateValid = new Template(
				title: TemplateNames.BORN,
				parts: Lists.newArrayList(
						new Template.Part(key: TemplateParam.FIRST, value: DAY_START_STRING),
						new Template.Part(key: TemplateParam.SECOND, value: MONTH_START_STRING),
						new Template.Part(key: TemplateParam.THIRD, value: YEAR_START_STRING),
						new Template.Part(key: TemplateParam.FOURTH, value: "ignored"),
						new Template.Part(key: TemplateParam.FIFTH, value: DAY_END_STRING),
						new Template.Part(key: TemplateParam.SIXTH, value: MONTH_END_STRING),
						new Template.Part(key: TemplateParam.SEVENTH, value: YEAR_END_STRING)
				)
		)
		templateInvalid = new Template(
				title: TemplateNames.BORN,
				parts: Lists.newArrayList())
	}

	def "valid values are passed to DayMonthYearProcessor"() {
		given:
		LocalDate dateOfBirth = LocalDate.of(YEAR_START, MONTH_START, DAY_START)
		LocalDate dateOfDeath = LocalDate.of(YEAR_END, MONTH_END, DAY_END)

		when:
		DateRange dateRange = pageToLifeRangeProcessor.process(new Page(templates: Lists.newArrayList(templateValid)))

		then:
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == DAY_START_STRING
			assert dayMonthYearCandidate.month == MONTH_START_STRING
			assert dayMonthYearCandidate.year == YEAR_START_STRING
			return dateOfBirth
		}
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == DAY_END_STRING
			assert dayMonthYearCandidate.month == MONTH_END_STRING
			assert dayMonthYearCandidate.year == YEAR_END_STRING
			return dateOfDeath
		}
		dateRange.startDate == dateOfBirth
		dateRange.endDate == dateOfDeath
	}

	def "null values are tolerated and produces null output"() {
		when:
		DateRange dateRange = pageToLifeRangeProcessor.process(new Page(templates: Lists.newArrayList(templateInvalid)))

		then:
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == null
			assert dayMonthYearCandidate.month == null
			assert dayMonthYearCandidate.year == null
			return null
		}
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == null
			assert dayMonthYearCandidate.month == null
			assert dayMonthYearCandidate.year == null
			return null
		}
		dateRange == null
	}

	def "returns null when no 'born' templates are found"() {
		when:
		DateRange dateRange = pageToLifeRangeProcessor.process(new Page(templates: Lists.newArrayList()))

		then:
		dateRange == null
	}

	def "returns null when more than one 'born' template is found"() {
		given:
		Page pageMock = Mock(Page) {
			getTemplates() >> Lists.newArrayList(templateValid, templateInvalid)
		}

		when:
		DateRange dateRange = pageToLifeRangeProcessor.process(pageMock)

		then:
		dateRange == null

		then: 'page title is used for logging'
		1 * pageMock.getTitle()
	}

}
