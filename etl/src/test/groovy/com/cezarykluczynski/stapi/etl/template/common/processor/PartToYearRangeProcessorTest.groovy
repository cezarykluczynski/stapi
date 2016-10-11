package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.YearRange
import com.cezarykluczynski.stapi.util.constants.TemplateNames
import com.cezarykluczynski.stapi.wiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class PartToYearRangeProcessorTest extends Specification {

	private static final Template START_TEMPLATE = new Template(title: TemplateNames.Y)
	private static final Template END_TEMPLATE = new Template(title: TemplateNames.Y)
	private static final Integer START_YEAR = 1997
	private static final Integer END_YEAR = 2005

	private TemplateToYearProcessor templateToYearProcessorMock

	private PartToYearRangeProcessor partToYearRangeProcessor

	def setup() {
		templateToYearProcessorMock = Mock(TemplateToYearProcessor)
		partToYearRangeProcessor = new PartToYearRangeProcessor(templateToYearProcessorMock)
	}

	def "returns empty YearRange when value is null, templates are null"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart(null, null))

		then:
		yearRange.startYear == null
		yearRange.endYear == null
	}

	def "returns start date when value contains a single number"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart("1995", null))

		then:
		yearRange.startYear == 1995
		yearRange.endYear == null
	}

	def "returns start date and end date, when value contains then, separated by &ndash;"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart("1990&ndash;2000", null))

		then:
		yearRange.startYear == 1990
		yearRange.endYear == 2000
	}

	def "valid Part is converted to YearRange with both dates"() {
		given:
		Template.Part part = createTemplatePart(null, Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE
		))

		when:
		YearRange yearRange = partToYearRangeProcessor.process(part)

		then:
		1 * templateToYearProcessorMock.process(START_TEMPLATE) >> START_YEAR
		1 * templateToYearProcessorMock.process(END_TEMPLATE) >> END_YEAR
		yearRange.startYear == START_YEAR
		yearRange.endYear == END_YEAR
	}

	def "Part with only start year is converted to YearRange with only start year"() {
		given:
		Template.Part part = createTemplatePart(null, Lists.newArrayList(
				START_TEMPLATE
		))

		when:
		YearRange yearRange = partToYearRangeProcessor.process(part)

		then: 'only start year is parsed'
		1 * templateToYearProcessorMock.process(START_TEMPLATE) >> START_YEAR

		yearRange.startYear == START_YEAR

		then: 'there is no end date'
		yearRange.endYear == null

		then: 'no other interactions are expected'
		0 * _
	}

	def "Part with more than 2 year templates results in empty YearRange"() {
		given:
		Template.Part part = createTemplatePart(null, Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE, START_TEMPLATE
		))

		when:
		YearRange yearRange = partToYearRangeProcessor.process(part)

		then: 'both years are null'
		yearRange.startYear == null
		yearRange.endYear == null

		then: 'no interactions are expected'
		0 * _
	}

	private static Template.Part createTemplatePart(String value, List<Template> templates) {
		return new Template.Part(value: value, templates: templates)
	}

}