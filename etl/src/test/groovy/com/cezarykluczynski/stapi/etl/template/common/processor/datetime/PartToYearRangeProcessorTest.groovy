package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class PartToYearRangeProcessorTest extends Specification {

	private static final Template START_TEMPLATE = new Template(title: TemplateName.Y)
	private static final Template END_TEMPLATE = new Template(title: TemplateName.YEARLINK)
	private static final Integer START_YEAR = 1997
	private static final Integer END_YEAR = 2005

	private YearlinkToYearProcessor templateToYearProcessorMock

	private TemplateFilter templateFilterMock

	private PartToYearRangeProcessor partToYearRangeProcessor

	void setup() {
		templateToYearProcessorMock = Mock(YearlinkToYearProcessor)
		templateFilterMock = Mock(TemplateFilter)
		partToYearRangeProcessor = new PartToYearRangeProcessor(templateToYearProcessorMock, templateFilterMock)
	}

	void "returns empty YearRange when value is null, templates are null"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart(null, null))

		then:
		yearRange.startYear == null
		yearRange.endYear == null
	}

	void "returns start date when value contains a single number"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('1995', null))

		then:
		yearRange.startYear == 1995
		yearRange.endYear == null
	}

	void "returns start date and end date, when value contains then, separated by &ndash;"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('1990&ndash;2000', null))

		then:
		yearRange.startYear == 1990
		yearRange.endYear == 2000
	}

	void "returns start date and end date, when value contains then, separated by ' to '"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('1990 to 2000', null))

		then:
		yearRange.startYear == 1990
		yearRange.endYear == 2000
	}

	void "valid Part is converted to YearRange with both dates"() {
		given:
		List<Template> templateList = Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE
		)
		Template.Part part = createTemplatePart(null, templateList)

		when:
		YearRange yearRange = partToYearRangeProcessor.process(part)

		then: 'date templates are filtered'
		1 * templateFilterMock.filterByTitle(templateList, TemplateName.Y, TemplateName.YEARLINK) >> templateList

		then:
		1 * templateToYearProcessorMock.process(START_TEMPLATE) >> START_YEAR
		1 * templateToYearProcessorMock.process(END_TEMPLATE) >> END_YEAR
		yearRange.startYear == START_YEAR
		yearRange.endYear == END_YEAR
	}

	void "Part with only start year is converted to YearRange with only start year"() {
		given:
		List<Template> templateList = Lists.newArrayList(
				START_TEMPLATE
		)
				Template.Part part = createTemplatePart(null, templateList)

		when:
		YearRange yearRange = partToYearRangeProcessor.process(part)

		then: 'date templates are filtered'
		1 * templateFilterMock.filterByTitle(templateList, TemplateName.Y, TemplateName.YEARLINK) >> templateList

		then: 'only start year is parsed'
		1 * templateToYearProcessorMock.process(START_TEMPLATE) >> START_YEAR

		yearRange.startYear == START_YEAR

		then: 'there is no end date'
		yearRange.endYear == null

		then: 'no other interactions are expected'
		0 * _
	}

	void "Part with more than 2 year templates results in empty YearRange"() {
		given:
		List<Template> templateList = Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE, START_TEMPLATE
		)
		Template.Part part = createTemplatePart(null, templateList)

		when:
		YearRange yearRange = partToYearRangeProcessor.process(part)

		then: 'date templates are filtered'
		1 * templateFilterMock.filterByTitle(templateList, TemplateName.Y, TemplateName.YEARLINK) >> templateList

		then: 'both years are null'
		yearRange.startYear == null
		yearRange.endYear == null

		then: 'no other interactions are expected'
		0 * _
	}

	private static Template.Part createTemplatePart(String value, List<Template> templates) {
		new Template.Part(
				value: value,
				templates: templates)
	}

}
