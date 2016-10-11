package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.util.constants.TemplateNames
import com.cezarykluczynski.stapi.wiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class PartToDateRangeProcessorTest extends Specification {

	private static final Template START_TEMPLATE = new Template(title: TemplateNames.D)
	private static final Template END_TEMPLATE = new Template(title: TemplateNames.D)
	private static final LocalDate START_DATE = LocalDate.of(2001, 1, 1)
	private static final LocalDate END_DATE = LocalDate.of(2012, 2, 2)

	private TemplateToLocalDateProcessor templateToLocalDateProcessorMock

	private PartToDateRangeProcessor partToDateRangeProcessor

	def setup() {
		templateToLocalDateProcessorMock = Mock(TemplateToLocalDateProcessor)
		partToDateRangeProcessor = new PartToDateRangeProcessor(templateToLocalDateProcessorMock)
	}

	def "valid Part is converted to DateRange with both dates"() {
		given:
		Template.Part part = new Template.Part(templates: Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE
		))

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then:
		1 * templateToLocalDateProcessorMock.process(START_TEMPLATE) >> START_DATE
		1 * templateToLocalDateProcessorMock.process(END_TEMPLATE) >> END_DATE
		dateRange.startDate == START_DATE
		dateRange.endDate == END_DATE
	}

	def "Part with only start date is converted to DateRange with only start date"() {
		given:
		Template.Part part = new Template.Part(templates: Lists.newArrayList(
				START_TEMPLATE
		))

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then: 'only start date is parsed'
		1 * templateToLocalDateProcessorMock.process(START_TEMPLATE) >> START_DATE

		dateRange.startDate == START_DATE

		then: 'there is no end date'
		dateRange.endDate == null

		then: 'no other interactions are expected'
		0 * _
	}

	def "Part with more than 2 date templates results in empty DateRange"() {
		given:
		Template.Part part = new Template.Part(templates: Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE, START_TEMPLATE
		))

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then: 'both dates are null'
		dateRange.startDate == null
		dateRange.endDate == null

		then: 'no interactions are expected'
		0 * _
	}

}
