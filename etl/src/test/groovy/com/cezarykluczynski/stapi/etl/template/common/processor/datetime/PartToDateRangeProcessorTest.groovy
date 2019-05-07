package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class PartToDateRangeProcessorTest extends Specification {

	private static final Template START_TEMPLATE = new Template(title: TemplateTitle.D)
	private static final Template END_TEMPLATE = new Template(title: TemplateTitle.D)
	private static final LocalDate START_DATE = LocalDate.of(2001, 1, 1)
	private static final LocalDate END_DATE = LocalDate.of(2012, 2, 2)

	private DatelinkTemplateToLocalDateProcessor templateToLocalDateProcessorMock

	private TemplateFilter templateFilterMock

	private PartToDateRangeProcessor partToDateRangeProcessor

	void setup() {
		templateToLocalDateProcessorMock = Mock()
		templateFilterMock = Mock()
		partToDateRangeProcessor = new PartToDateRangeProcessor(templateToLocalDateProcessorMock, templateFilterMock)
	}

	void "valid Part is converted to DateRange with both dates"() {
		given:
		List<Template> templateList = Lists.newArrayList(START_TEMPLATE, END_TEMPLATE)
		Template.Part part = new Template.Part(templates: templateList)

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then: 'date templates are filtered'
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK) >> templateList

		then:
		1 * templateToLocalDateProcessorMock.process(START_TEMPLATE) >> START_DATE
		1 * templateToLocalDateProcessorMock.process(END_TEMPLATE) >> END_DATE
		dateRange.startDate == START_DATE
		dateRange.endDate == END_DATE
	}

	void "Part with only start date is converted to DateRange with only start date"() {
		given:
		List<Template> templateList = Lists.newArrayList(START_TEMPLATE)
		Template.Part part = new Template.Part(templates: templateList)

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then: 'date templates are filtered'
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK) >> templateList

		then: 'only start date is parsed'
		1 * templateToLocalDateProcessorMock.process(START_TEMPLATE) >> START_DATE

		dateRange.startDate == START_DATE

		then: 'there is no end date'
		dateRange.endDate == null

		then: 'no other interactions are expected'
		0 * _
	}

	void "Part with no templates is converted to empty DateRange"() {
		given:
		Template.Part part = new Template.Part(templates: [])

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then: 'both dates are null'
		dateRange.startDate == null
		dateRange.endDate == null

		then: 'no other interactions are expected'
		0 * _
	}

	void "Part with more than 2 date templates results in empty DateRange"() {
		given:
		List<Template> templateList = Lists.newArrayList(START_TEMPLATE, END_TEMPLATE, START_TEMPLATE)
		Template.Part part = new Template.Part(templates: templateList)

		when:
		DateRange dateRange = partToDateRangeProcessor.process(part)

		then: 'date templates are filtered'
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK) >> templateList

		then: 'both dates are null'
		dateRange.startDate == null
		dateRange.endDate == null

		then: 'no other interactions are expected'
		0 * _
	}

}
