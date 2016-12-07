package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class PartToDateRangeProcessorTest extends Specification {

	private static final Template START_TEMPLATE = new Template(title: TemplateName.D)
	private static final Template END_TEMPLATE = new Template(title: TemplateName.D)
	private static final LocalDate START_DATE = LocalDate.of(2001, 1, 1)
	private static final LocalDate END_DATE = LocalDate.of(2012, 2, 2)

	private DatelinkTemplateToLocalDateProcessor templateToLocalDateProcessorMock

	private TemplateFilter templateFilterMock

	private PartToDateRangeProcessor partToDateRangeProcessor

	def setup() {
		templateToLocalDateProcessorMock = Mock(DatelinkTemplateToLocalDateProcessor)
		templateFilterMock = Mock(TemplateFilter)
		partToDateRangeProcessor = new PartToDateRangeProcessor(templateToLocalDateProcessorMock, templateFilterMock)
	}

	def "valid Part is converted to DateRange with both dates"() {
		given:
		List<Template> templateList = Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE
		)
		Template.Part part = new Template.Part(templates: templateList)

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then: 'date templates are filtered'
		1 * templateFilterMock.filterByTitle(templateList, TemplateName.D, TemplateName.DATELINK) >> templateList

		then:
		1 * templateToLocalDateProcessorMock.process(START_TEMPLATE) >> START_DATE
		1 * templateToLocalDateProcessorMock.process(END_TEMPLATE) >> END_DATE
		dateRange.startDate == START_DATE
		dateRange.endDate == END_DATE
	}

	def "Part with only start date is converted to DateRange with only start date"() {
		given:
		List<Template> templateList = Lists.newArrayList(
				START_TEMPLATE
		)
				Template.Part part = new Template.Part(templates: templateList)

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then: 'date templates are filtered'
		1 * templateFilterMock.filterByTitle(templateList, TemplateName.D, TemplateName.DATELINK) >> templateList

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
		List<Template> templateList = Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE, START_TEMPLATE
		)
		Template.Part part = new Template.Part(templates: templateList)

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then: 'date templates are filtered'
		1 * templateFilterMock.filterByTitle(templateList, TemplateName.D, TemplateName.DATELINK) >> templateList

		then: 'both dates are null'
		dateRange.startDate == null
		dateRange.endDate == null

		then: 'no other interactions are expected'
		0 * _
	}

}
