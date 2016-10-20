package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateToLifeRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.util.constants.TemplateNames
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.cezarykluczynski.stapi.wiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class LifeRangeProcessorTest extends Specification {

	private PageToLifeRangeProcessor pageToLifeRangeProcessorMock

	private ActorTemplateToLifeRangeProcessor actorTemplateToLifeRangeProcessorMock

	private LifeRangeProcessor lifeRangeProcessor

	private Template template

	private Page page

	def setup() {
		pageToLifeRangeProcessorMock = Mock(PageToLifeRangeProcessor)
		actorTemplateToLifeRangeProcessorMock = Mock(ActorTemplateToLifeRangeProcessor)
		lifeRangeProcessor = new LifeRangeProcessor(pageToLifeRangeProcessorMock, actorTemplateToLifeRangeProcessorMock)
		template = new Template(title: TemplateNames.SIDEBAR_ACTOR)
		page = Mock(Page) {
			getTemplates() >> Lists.newArrayList(template)
		}
	}

	def "returns empty date range when both subprocessors returned null"() {
		given:
		Page page = new Page()

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		dateRangeOutput.startDate == null
		dateRangeOutput.endDate == null
	}

	def "returns value from PageToLifeRangeProcessor if it is the only present"() {
		given:
		Page page = new Page()
		DateRange dateRange = new DateRange()

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * pageToLifeRangeProcessorMock.process(page) >> dateRange
		dateRangeOutput == dateRange
	}

	def "returns value from ActorTemplateToLifeRangeProcessor if it is the only present"() {
		given:
		DateRange dateRange = new DateRange()

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> dateRange
		dateRangeOutput == dateRange
	}

	def "return null if both subprocessors returns nulls"() {
		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * pageToLifeRangeProcessorMock.process(page) >> new DateRange()
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> new DateRange()
		dateRangeOutput.startDate == null
		dateRangeOutput.endDate == null
	}

	def "returns values of both subprocessors if they are equal"() {
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
		1 * pageToLifeRangeProcessorMock.process(page) >> pageDateRange
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> actorTemplateDateRange
		dateRangeOutput.startDate == LocalDate.of(1960, 10, 10)
		dateRangeOutput.endDate == LocalDate.of(2030, 11, 11)
	}

	def "returns values of one of subprocessors if only value is present"() {
		given:
		DateRange pageDateRange = new DateRange(
				startDate: LocalDate.of(1960, 10, 10))
		DateRange actorTemplateDateRange = new DateRange(
				endDate: LocalDate.of(2030, 11, 11))

		when:
		DateRange dateRangeOutput = lifeRangeProcessor.process(page)

		then:
		1 * pageToLifeRangeProcessorMock.process(page) >> pageDateRange
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> actorTemplateDateRange
		dateRangeOutput.startDate == LocalDate.of(1960, 10, 10)
		dateRangeOutput.endDate == LocalDate.of(2030, 11, 11)
	}

	def "returns nulls when values from both subprocessors differs"() {
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
		1 * pageToLifeRangeProcessorMock.process(page) >> pageDateRange
		1 * actorTemplateToLifeRangeProcessorMock.process(template) >> actorTemplateDateRange
		dateRangeOutput.startDate == null
		dateRangeOutput.endDate == null

		then: 'page title is used for logging'
		2 * page.getTitle()
	}

}
