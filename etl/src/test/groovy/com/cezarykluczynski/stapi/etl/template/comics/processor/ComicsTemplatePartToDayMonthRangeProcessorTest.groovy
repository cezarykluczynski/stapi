package com.cezarykluczynski.stapi.etl.template.comics.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.service.TemplateToDayMonthYearParser
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicsTemplatePartToDayMonthRangeProcessorTest extends Specification {

	private TemplateFilter templateFilterMock

	private TemplateToDayMonthYearParser templateToDayMonthYearParserMock

	private ComicsTemplatePartToDayMonthRangeProcessor comicsTemplatePartToDayMonthRangeProcessor

	void setup() {
		templateFilterMock = Mock(TemplateFilter)
		templateToDayMonthYearParserMock = Mock(TemplateToDayMonthYearParser)
		comicsTemplatePartToDayMonthRangeProcessor = new ComicsTemplatePartToDayMonthRangeProcessor(templateFilterMock,
				templateToDayMonthYearParserMock)
	}

	void "when one datelink template is found in template part, it is used to to parse day month year"() {
		given:
		DayMonthYear dayMonthYear = Mock(DayMonthYear)
		Template datelinkTemplate = Mock(Template)
		List<Template> templateList = Mock(List)
		Template.Part templatePart = new Template.Part(templates: templateList)

		when:
		DayMonthYear dayMonthYearOutput = comicsTemplatePartToDayMonthRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList(datelinkTemplate)
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList()
		1 * templateToDayMonthYearParserMock.parseDayMonthYearCandidate(datelinkTemplate) >> dayMonthYear
		0 * _
		dayMonthYearOutput == dayMonthYear
	}

	void "when more than one datelink template is found in template part, the first one is used"() {
		given:
		DayMonthYear dayMonthYear = Mock(DayMonthYear)
		Template datelinkTemplate1 = Mock(Template)
		Template datelinkTemplate2 = Mock(Template)
		List<Template> templateList = Mock(List)
		Template.Part templatePart = new Template.Part(templates: templateList)

		when:
		DayMonthYear dayMonthYearOutput = comicsTemplatePartToDayMonthRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK) >> Lists
				.newArrayList(datelinkTemplate1, datelinkTemplate2)
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList()
		1 * templateToDayMonthYearParserMock.parseDayMonthYearCandidate(datelinkTemplate1) >> dayMonthYear
		0 * _
		dayMonthYearOutput == dayMonthYear
	}

	void "when one monthlink template is found in template part, it is used to to parse month year"() {
		given:
		DayMonthYear dayMonthYear = Mock(DayMonthYear)
		Template monthlinkTemplate = Mock(Template)
		List<Template> templateList = Mock(List)
		Template.Part templatePart = new Template.Part(templates: templateList)

		when:
		DayMonthYear dayMonthYearOutput = comicsTemplatePartToDayMonthRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList(monthlinkTemplate)
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList()
		1 * templateToDayMonthYearParserMock.parseMonthYearCandidate(monthlinkTemplate) >> dayMonthYear
		0 * _
		dayMonthYearOutput == dayMonthYear
	}

	void "when more than one monthlink template is found in template part, the first one is used"() {
		given:
		DayMonthYear dayMonthYear = Mock(DayMonthYear)
		Template monthlinkTemplate1 = Mock(Template)
		Template monthlinkTemplate2 = Mock(Template)
		List<Template> templateList = Mock(List)
		Template.Part templatePart = new Template.Part(templates: templateList)

		when:
		DayMonthYear dayMonthYearOutput = comicsTemplatePartToDayMonthRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists
				.newArrayList(monthlinkTemplate1, monthlinkTemplate2)
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList()
		1 * templateToDayMonthYearParserMock.parseMonthYearCandidate(monthlinkTemplate1) >> dayMonthYear
		0 * _
		dayMonthYearOutput == dayMonthYear
	}

	void "when one yearlink template is found in template part, it is used to to parse year"() {
		given:
		DayMonthYear dayMonthYear = Mock(DayMonthYear)
		Template yearlinkTemplate = Mock(Template)
		List<Template> templateList = Mock(List)
		Template.Part templatePart = new Template.Part(templates: templateList)

		when:
		DayMonthYear dayMonthYearOutput = comicsTemplatePartToDayMonthRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList(yearlinkTemplate)
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplate) >> dayMonthYear
		0 * _
		dayMonthYearOutput == dayMonthYear
	}

	void "when more than one yearlink template is found in template part, the first one is used"() {
		given:
		DayMonthYear dayMonthYear = Mock(DayMonthYear)
		Template yearlinkTemplate1 = Mock(Template)
		Template yearlinkTemplate2 = Mock(Template)
		List<Template> templateList = Mock(List)
		Template.Part templatePart = new Template.Part(templates: templateList)

		when:
		DayMonthYear dayMonthYearOutput = comicsTemplatePartToDayMonthRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists
				.newArrayList(yearlinkTemplate1, yearlinkTemplate2)
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplate1) >> dayMonthYear
		0 * _
		dayMonthYearOutput == dayMonthYear
	}

}
