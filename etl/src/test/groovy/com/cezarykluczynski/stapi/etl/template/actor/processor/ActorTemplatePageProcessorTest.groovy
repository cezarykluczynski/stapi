package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor
import com.cezarykluczynski.stapi.util.constants.CategoryName
import com.cezarykluczynski.stapi.util.constants.PageNames
import com.cezarykluczynski.stapi.util.constants.TemplateNames
import com.cezarykluczynski.stapi.wiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.cezarykluczynski.stapi.wiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class ActorTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (actor)'
	private static final String NAME = 'NAME'
	private static final String BIRTH_NAME = 'BIRTH_NAME'
	private static final Gender GENDER = Gender.F
	private static final DateRange LIFE_RANGE = new DateRange()

	private PageToGenderProcessor pageToGenderProcessorMock

	private PageToLifeRangeProcessor pageToLifeRangeProcessorMock

	private ActorTemplateTemplateProcessor actorTemplateTemplateProcessorMock

	private ActorTemplatePageProcessor actorTemplatePageProcessor

	private Template template

	private Page pageWithTemplate

	def setup() {
		pageToGenderProcessorMock = Mock(PageToGenderProcessor)
		pageToLifeRangeProcessorMock = Mock(PageToLifeRangeProcessor)
		actorTemplateTemplateProcessorMock = Mock(ActorTemplateTemplateProcessor)
		actorTemplatePageProcessor = new ActorTemplatePageProcessor(pageToGenderProcessorMock,
				pageToLifeRangeProcessorMock, actorTemplateTemplateProcessorMock)

		template = new Template(title: TemplateNames.SIDEBAR_ACTOR)
		pageWithTemplate = new Page(templates: Lists.newArrayList(
				template
		))
	}

	def "unknown performs page should produce null template"() {
		given:
		Page page = new Page(title: PageNames.UNKNOWN_PERFORMERS)

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(page)

		then:
		actorTemplate == null
	}

	def "page with production lists category should produce null template"() {
		given:
		Page page = new Page(categories: Lists.newArrayList(new CategoryHeader(title: CategoryName.PRODUCTION_LISTS)))

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(page)

		then:
		actorTemplate == null
	}

	def "sets name from page title"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(page)

		then:
		actorTemplate.name == TITLE
	}

	def "sets name from page title, and cuts brackets when they are present"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(page)

		then:
		actorTemplate.name == TITLE
	}

	def "sets gender from PageToGenderProcessor"() {
		given:
		Page page = new Page()

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(page)

		then:
		1 * pageToGenderProcessorMock.process(page) >> GENDER
		actorTemplate.gender == GENDER
	}

	def "sets life range from PageToLifeRangeProcessor"() {
		given:
		Page page = new Page()

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(page)

		then:
		1 * pageToLifeRangeProcessorMock.process(page) >> LIFE_RANGE
		actorTemplate.lifeRange == LIFE_RANGE
	}

	def "uses name from subprocessor, if it is present"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(name: NAME)

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(pageWithTemplate)

		then:
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.name == NAME
	}

	def "uses birth name from subprocessor, if it is present"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(birthName: BIRTH_NAME)

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(pageWithTemplate)

		then:
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.birthName == BIRTH_NAME
	}

	def "uses gender from subprocessor only if current gender is null"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(gender: Gender.F)

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(pageWithTemplate)

		then:
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.gender == Gender.F
	}

	def "sets gender to null when genders found by ActorTemplateTemplateProcessor and ActorTemplatePageProcessor differs"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(gender: Gender.F)

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(pageWithTemplate)

		then:
		1 * pageToGenderProcessorMock.process(pageWithTemplate) >> Gender.M
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.gender == null
	}

}
