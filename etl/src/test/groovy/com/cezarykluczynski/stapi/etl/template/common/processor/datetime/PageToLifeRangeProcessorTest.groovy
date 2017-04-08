package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
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
	private static final String YEAR_START_STRING = YEAR_START
	private static final String MONTH_START_STRING = MONTH_START
	private static final String DAY_START_STRING = DAY_START
	private static final String YEAR_END_STRING = YEAR_END
	private static final String MONTH_END_STRING = MONTH_END
	private static final String DAY_END_STRING = DAY_END

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessorMock

	private PageToLifeRangeProcessor pageToLifeRangeProcessor

	private Template templateValid

	private Template templateInvalid

	void setup() {
		dayMonthYearCandidateToLocalDateProcessorMock = Mock()
		pageToLifeRangeProcessor = new PageToLifeRangeProcessor(dayMonthYearCandidateToLocalDateProcessorMock)

		templateValid = new Template(
				title: TemplateTitle.BORN,
				parts: Lists.newArrayList(
						new Template.Part(key: TemplateParam.FIRST, value: DAY_START_STRING),
						new Template.Part(key: TemplateParam.SECOND, value: MONTH_START_STRING),
						new Template.Part(key: TemplateParam.THIRD, value: YEAR_START_STRING),
						new Template.Part(key: TemplateParam.FOURTH, value: 'ignored'),
						new Template.Part(key: TemplateParam.FIFTH, value: DAY_END_STRING),
						new Template.Part(key: TemplateParam.SIXTH, value: MONTH_END_STRING),
						new Template.Part(key: TemplateParam.SEVENTH, value: YEAR_END_STRING)
				)
		)
		templateInvalid = new Template(
				title: TemplateTitle.BORN,
				parts: Lists.newArrayList())
	}

	void "valid values are passed to DayMonthYearProcessor"() {
		given:
		LocalDate dateOfBirth = LocalDate.of(YEAR_START, MONTH_START, DAY_START)
		LocalDate dateOfDeath = LocalDate.of(YEAR_END, MONTH_END, DAY_END)

		when:
		DateRange dateRange = pageToLifeRangeProcessor.process(new Page(templates: Lists.newArrayList(templateValid)))

		then:
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == DAY_START_STRING
			assert dayMonthYearCandidate.month == MONTH_START_STRING
			assert dayMonthYearCandidate.year == YEAR_START_STRING
			dateOfBirth
		}
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == DAY_END_STRING
			assert dayMonthYearCandidate.month == MONTH_END_STRING
			assert dayMonthYearCandidate.year == YEAR_END_STRING
			dateOfDeath
		}
		dateRange.startDate == dateOfBirth
		dateRange.endDate == dateOfDeath
	}

	void "null values are tolerated and produces null output"() {
		when:
		DateRange dateRange = pageToLifeRangeProcessor.process(new Page(templates: Lists.newArrayList(templateInvalid)))

		then:
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == null
			assert dayMonthYearCandidate.month == null
			assert dayMonthYearCandidate.year == null
			null
		}
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == null
			assert dayMonthYearCandidate.month == null
			assert dayMonthYearCandidate.year == null
			null
		}
		dateRange == null
	}

	void "returns null when no 'born' templates are found"() {
		when:
		DateRange dateRange = pageToLifeRangeProcessor.process(new Page(templates: Lists.newArrayList()))

		then:
		dateRange == null
	}

	void "returns null when more than one 'born' template is found"() {
		given:
		Page page = Mock()

		when:
		DateRange dateRange = pageToLifeRangeProcessor.process(page)

		then:
		1 * page.templates >> Lists.newArrayList(templateValid, templateInvalid)
		dateRange == null

		then: 'page title is used for logging'
		1 * page.title
	}

}
