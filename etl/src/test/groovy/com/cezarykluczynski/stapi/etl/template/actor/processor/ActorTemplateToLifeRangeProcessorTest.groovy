package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToLocalDateProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class ActorTemplateToLifeRangeProcessorTest extends Specification {

	private static final String INVALID_TEMPLATE_NAME = 'INVALID_TEMPLATE_NAME'

	private DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessorMock

	private TemplateFilter templateFilterMock

	private ActorTemplateToLifeRangeProcessor actorTemplateToLifeRangeProcessor

	void setup() {
		datelinkTemplateToLocalDateProcessorMock = Mock()
		templateFilterMock = Mock()
		actorTemplateToLifeRangeProcessor = new ActorTemplateToLifeRangeProcessor(datelinkTemplateToLocalDateProcessorMock, templateFilterMock)
	}

	void "template of different title produces null LocalDate"() {
		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(new Template(title: 'different template'))

		then:
		dateRange == null
	}

	void "gets date of birth and date of death from child tempaltes"() {
		LocalDate dateOfBirth = LocalDate.of(1955, 2, 2)
		LocalDate dateOfDeath = LocalDate.of(2009, 5, 5)

		given:
		Template templateDateOfBirth = new Template(title: TemplateTitle.D)
		Template templateDateOfDeath = new Template(title: TemplateTitle.DATELINK)
		List<Template> templatesDateOfBirth = Lists.newArrayList(templateDateOfBirth)
		List<Template> templatesDateOfDeath = Lists.newArrayList(templateDateOfDeath)
		Template template = new Template(title: TemplateTitle.SIDEBAR_ACTOR,
				parts: Lists.newArrayList(
						new Template.Part(
								key: ActorTemplateParameter.DATE_OF_BIRTH,
								templates: templatesDateOfBirth),
						new Template.Part(
								key: ActorTemplateParameter.DATE_OF_DEATH,
								templates: templatesDateOfDeath)))

		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(template)

		then:
		1 * templateFilterMock.filterByTitle(templatesDateOfBirth, TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList(templateDateOfBirth)
		1 * templateFilterMock.filterByTitle(templatesDateOfDeath, TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList(templatesDateOfDeath)
		1 * datelinkTemplateToLocalDateProcessorMock.process(templateDateOfBirth) >> dateOfBirth
		1 * datelinkTemplateToLocalDateProcessorMock.process(templateDateOfDeath) >> dateOfDeath
		dateRange.startDate == dateOfBirth
		dateRange.endDate == dateOfDeath
	}

	void "returns null when no 'd' or 'datelink' templates were found"() {
		given:
		Template template = new Template(title: TemplateTitle.SIDEBAR_ACTOR,
				parts: Lists.newArrayList())

		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(template)

		then:
		dateRange == null
	}

	void "returns null when templates child templates list is empty or does not contain 'd' nor 'datelink' templates"() {
		given:
		Template invalidTemplate = new Template(title: INVALID_TEMPLATE_NAME)
		Template template = new Template(title: TemplateTitle.SIDEBAR_ACTOR,
				parts: Lists.newArrayList(
						new Template.Part(
								key: ActorTemplateParameter.DATE_OF_BIRTH,
								templates: Lists.newArrayList()),
						new Template.Part(
								key: ActorTemplateParameter.DATE_OF_DEATH,
								templates: Lists.newArrayList(invalidTemplate))))

		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(template)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(invalidTemplate), TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		dateRange == null
	}

}
