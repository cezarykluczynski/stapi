package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateToLifeRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class LifeRangeProcessorTest extends Specification {

	private PageToLifeRangeProcessor pageToLifeRangeProcessorMock

	private ActorTemplateToLifeRangeProcessor actorTemplateToLifeRangeProcessorMock

	private TemplateFinder templateFinderMock

	private LifeRangeProcessor lifeRangeProcessor

	private Template template

	private Page page

	void setup() {
		pageToLifeRangeProcessorMock = Mock()
		actorTemplateToLifeRangeProcessorMock = Mock()
		templateFinderMock = Mock()
		lifeRangeProcessor = new LifeRangeProcessor(pageToLifeRangeProcessorMock, actorTemplateToLifeRangeProcessorMock,
				templateFinderMock)
		template = new Template(title: TemplateTitle.SIDEBAR_ACTOR)
		page = Mock()
		page.templates >> Lists.newArrayList(template)
	}

	void "returns empty date range when both subprocessors returned null"() {
		given:
		Page page = new Page()

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR) >> Optional.empty()
		dateRangeOutput.startDate == null
		dateRangeOutput.endDate == null
	}

	void "returns value from PageToLifeRangeProcessor if it is the only present"() {
		given:
		Page page = new Page()
		DateRange dateRange = new DateRange()

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR) >> Optional.empty()
		1 * pageToLifeRangeProcessorMock.process(page) >> dateRange
		dateRangeOutput == dateRange
	}

	void "returns value from ActorTemplateToLifeRangeProcessor if it is the only present"() {
		given:
		DateRange dateRange = new DateRange()

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR) >> Optional.of(template)
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> dateRange
		dateRangeOutput == dateRange
	}

	void "return null if both subprocessors returns nulls"() {
		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR) >> Optional.of(template)
		1 * pageToLifeRangeProcessorMock.process(page) >> new DateRange()
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> new DateRange()
		dateRangeOutput.startDate == null
		dateRangeOutput.endDate == null
	}

	void "returns values of both subprocessors if they are equal"() {
		given:
		DateRange pageDateRange = new DateRange(
				startDate: LocalDate.of(1960, 10, 10),
				endDate: LocalDate.of(2030, 11, 11))
		DateRange actorTemplateDateRange = new DateRange(
				startDate: LocalDate.of(1960, 10, 10),
				endDate: LocalDate.of(2030, 11, 11))

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR) >> Optional.of(template)
		1 * pageToLifeRangeProcessorMock.process(page) >> pageDateRange
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> actorTemplateDateRange
		dateRangeOutput.startDate == LocalDate.of(1960, 10, 10)
		dateRangeOutput.endDate == LocalDate.of(2030, 11, 11)
	}

	void "returns values of one of subprocessors if only value is present"() {
		given:
		DateRange pageDateRange = new DateRange(
				startDate: LocalDate.of(1960, 10, 10))
		DateRange actorTemplateDateRange = new DateRange(
				endDate: LocalDate.of(2030, 11, 11))

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR) >> Optional.of(template)
		1 * pageToLifeRangeProcessorMock.process(page) >> pageDateRange
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> actorTemplateDateRange
		dateRangeOutput.startDate == LocalDate.of(1960, 10, 10)
		dateRangeOutput.endDate == LocalDate.of(2030, 11, 11)
	}

	void "returns nulls when values from both subprocessors differs"() {
		given:
		DateRange pageDateRange = new DateRange(
				startDate: LocalDate.of(2030, 11, 11),
				endDate: LocalDate.of(1960, 10, 10))
		DateRange actorTemplateDateRange = new DateRange(
				startDate: LocalDate.of(1960, 10, 10),
				endDate: LocalDate.of(2030, 11, 11))

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR) >> Optional.of(template)
		1 * pageToLifeRangeProcessorMock.process(page) >> pageDateRange
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> actorTemplateDateRange
		dateRangeOutput.startDate == null
		dateRangeOutput.endDate == null

		then: 'page title is used for logging'
		2 * page.title
	}

}
