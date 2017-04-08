package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.performer.creation.processor.CategoriesActorTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.PageTitle
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class ActorTemplateSinglePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Long PAGE_ID = 1L
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN
	private static final String TITLE_WITH_BRACKETS = 'TITLE (actor)'
	private static final String NAME = 'NAME'
	private static final String BIRTH_NAME = 'BIRTH_NAME'
	private static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	private static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'
	private static final Gender GENDER = Gender.F
	private static final DateRange LIFE_RANGE = new DateRange()

	private PageToGenderProcessor pageToGenderProcessorMock

	private PageToLifeRangeProcessor pageToLifeRangeProcessorMock

	private ActorTemplateTemplateProcessor actorTemplateTemplateProcessorMock

	private CategoriesActorTemplateEnrichingProcessor categoriesActorTemplateEnrichingProcessorMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor

	private Template template

	private Page pageWithTemplate

	void setup() {
		pageToGenderProcessorMock = Mock()
		pageToLifeRangeProcessorMock = Mock()
		actorTemplateTemplateProcessorMock = Mock()
		categoriesActorTemplateEnrichingProcessorMock = Mock()
		pageBindingServiceMock = Mock()
		templateFinderMock = Mock()
		actorTemplateSinglePageProcessor = new ActorTemplateSinglePageProcessor(pageToGenderProcessorMock, pageToLifeRangeProcessorMock,
				actorTemplateTemplateProcessorMock, categoriesActorTemplateEnrichingProcessorMock, pageBindingServiceMock, templateFinderMock)

		template = new Template(title: TemplateTitle.SIDEBAR_ACTOR)
		pageWithTemplate = new Page(templates: Lists.newArrayList(
				template
		))
	}

	void "unknown performs page should produce null template"() {
		given:
		Page page = new Page(title: PageTitle.UNKNOWN_PERFORMERS)

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(page)

		then:
		actorTemplate == null
	}

	void "page with production lists category should produce null template"() {
		given:
		Page page = new Page(categories: Lists.newArrayList(new CategoryHeader(title: CategoryTitle.PRODUCTION_LISTS)))

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(page)

		then:
		actorTemplate == null
	}

	void "sets name and page from page title"() {
		given:
		Page page = new Page(
				title: TITLE,
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE
		)
		PageEntity pageEntity = new PageEntity()

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.empty()
		actorTemplate.name == TITLE
		actorTemplate.page == pageEntity
	}

	void "sets name from page title, when template name is 'sidebar crew'"() {
		given:
		Page page = new Page(title: TITLE)
		template.title == TemplateTitle.SIDEBAR_CREW

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.empty()
		actorTemplate.name == TITLE
	}

	void "sets name from page title, and cuts brackets when they are present"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.empty()
		actorTemplate.name == TITLE
	}

	void "sets page entity from wiki page dto"() {
		given:
		Page page = new Page(
				title: TITLE,
				pageId: PAGE_ID)
		PageEntity pageEntity = new PageEntity()

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		actorTemplate.page == pageEntity
	}

	void "sets gender from PageToGenderProcessor"() {
		given:
		Page page = new Page()

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.empty()
		1 * pageToGenderProcessorMock.process(page) >> GENDER
		actorTemplate.gender == GENDER
	}

	void "sets life range from PageToLifeRangeProcessor"() {
		given:
		Page page = new Page()

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.empty()
		1 * pageToLifeRangeProcessorMock.process(page) >> LIFE_RANGE
		actorTemplate.lifeRange == LIFE_RANGE
	}

	void "uses name from subprocessor, if it is present"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(name: NAME)

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(pageWithTemplate)

		then:
		1 * templateFinderMock.findTemplate(pageWithTemplate, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.of(template)
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.name == NAME
	}

	void "sets birth name from page content"() {
		given:
		pageWithTemplate.wikitext = "'''${BIRTH_NAME}''' is an actor."

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(pageWithTemplate)

		then:
		1 * templateFinderMock.findTemplate(pageWithTemplate, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.of(template)
		1 * actorTemplateTemplateProcessorMock.process(template) >> new ActorTemplate()
		actorTemplate.birthName == BIRTH_NAME
	}

	void "sets birth name from page content, then removes it if it equals name"() {
		given:
		pageWithTemplate.title = NAME
		pageWithTemplate.wikitext = "'''${NAME}''' is an actor."

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(pageWithTemplate)

		then:
		1 * templateFinderMock.findTemplate(pageWithTemplate, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.of(template)
		1 * actorTemplateTemplateProcessorMock.process(template) >> new ActorTemplate()
		actorTemplate.birthName == null
	}

	void "uses birth name from subprocessor, if it is present"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(birthName: BIRTH_NAME)

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(pageWithTemplate)

		then:
		1 * templateFinderMock.findTemplate(pageWithTemplate, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.of(template)
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.birthName == BIRTH_NAME
	}

	void "uses place of birth from subprocessor, if it is present"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(placeOfBirth: PLACE_OF_BIRTH)

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(pageWithTemplate)

		then:
		1 * templateFinderMock.findTemplate(pageWithTemplate, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.of(template)
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.placeOfBirth == PLACE_OF_BIRTH
	}

	void "uses place of death from subprocessor, if it is present"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(placeOfBirth: PLACE_OF_DEATH)

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(pageWithTemplate)

		then:
		1 * templateFinderMock.findTemplate(pageWithTemplate, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.of(template)
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.placeOfBirth == PLACE_OF_DEATH
	}

	void "uses gender from subprocessor only if current gender is null"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(gender: Gender.F)

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(pageWithTemplate)

		then:
		1 * templateFinderMock.findTemplate(pageWithTemplate, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.of(template)
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.gender == Gender.F
	}

	void "sets gender to null when genders found by ActorTemplateTemplateProcessor and ActorTemplatePageProcessor differs"() {
		given:
		ActorTemplate actorTemplateFromTemplate = new ActorTemplate(gender: Gender.F)

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(pageWithTemplate)

		then:
		1 * pageToGenderProcessorMock.process(pageWithTemplate) >> Gender.M
		1 * templateFinderMock.findTemplate(pageWithTemplate, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.of(template)
		1 * actorTemplateTemplateProcessorMock.process(template) >> actorTemplateFromTemplate
		actorTemplate.gender == null
	}

	void "uses CategoriesActorTemplateEnrichingProcessor to enrich ActorTemplate"() {
		given:
		actorTemplateTemplateProcessorMock.process(_) >> new ActorTemplate()

		when:
		ActorTemplate actorTemplate = actorTemplateSinglePageProcessor.process(pageWithTemplate)

		then:
		1 * templateFinderMock.findTemplate(pageWithTemplate, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW) >> Optional.empty()
		1 * categoriesActorTemplateEnrichingProcessorMock.enrich(_) >> { EnrichablePair<List<CategoryHeader>, ActorTemplate> enrichablePair ->
			enrichablePair.output.animalPerformer = true
		}
		actorTemplate.animalPerformer
	}

}
