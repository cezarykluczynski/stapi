package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToLocalDateProcessor
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class ActorTemplateToLifeRangeProcessorTest extends Specification {

	private DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessorMock

	private ActorTemplateToLifeRangeProcessor actorTemplateToLifeRangeProcessor

	def setup() {
		datelinkTemplateToLocalDateProcessorMock = Mock(DatelinkTemplateToLocalDateProcessor)
		actorTemplateToLifeRangeProcessor = new ActorTemplateToLifeRangeProcessor(
				datelinkTemplateToLocalDateProcessorMock)
	}

	def "template of different title produces null LocalDate"() {
		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(new Template(title: "different template"))

		then:
		dateRange == null
	}

	def "gets date of birth and date of death from child tempaltes"() {
		LocalDate dateOfBirth = LocalDate.of(1955, 2, 2)
		LocalDate dateOfDeath = LocalDate.of(2009, 5, 5)

		given:
		Template templateDateOfBirth = new Template(title: TemplateName.D)
		Template templateDateOfDeath = new Template(title: TemplateName.DATELINK)
		Template template = new Template(title: TemplateName.SIDEBAR_ACTOR,
				parts: Lists.newArrayList(
						new Template.Part(
								key: ActorTemplateToLifeRangeProcessor.KEY_DATE_OF_BIRTH,
								templates: Lists.newArrayList(templateDateOfBirth)),
						new Template.Part(
								key: ActorTemplateToLifeRangeProcessor.KEY_DATE_OF_DEATH,
								templates: Lists.newArrayList(templateDateOfDeath))))

		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(template)

		then:
		1 * datelinkTemplateToLocalDateProcessorMock.process(templateDateOfBirth) >> dateOfBirth
		1 * datelinkTemplateToLocalDateProcessorMock.process(templateDateOfDeath) >> dateOfDeath
		dateRange.startDate == dateOfBirth
		dateRange.endDate == dateOfDeath
	}

	def "returns null when no 'd' or 'datelink' templates were found"() {
		given:
		Template template = new Template(title: TemplateName.SIDEBAR_ACTOR,
				parts: Lists.newArrayList())

		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(template)

		then:
		dateRange == null
	}

	def "returns null when templates child templates list is empty or does not contain 'd' nor 'datelink' templates"() {
		given:
		Template template = new Template(title: TemplateName.SIDEBAR_ACTOR,
				parts: Lists.newArrayList(
						new Template.Part(
								key: ActorTemplateToLifeRangeProcessor.KEY_DATE_OF_BIRTH,
								templates: Lists.newArrayList()),
						new Template.Part(
								key: ActorTemplateToLifeRangeProcessor.KEY_DATE_OF_DEATH,
								templates: Lists.newArrayList(new Template(title: "invalid title")))))

		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(template)

		then:
		dateRange == null
	}

}
