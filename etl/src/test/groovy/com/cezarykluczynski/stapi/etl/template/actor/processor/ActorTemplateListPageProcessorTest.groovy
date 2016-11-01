package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.constant.PageName
import com.google.common.collect.Lists
import spock.lang.Specification

class ActorTemplateListPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Long PAGE_ID = 1L

	ActorTemplateListPageProcessor actorTemplateListPageProcessor

	def setup() {
		actorTemplateListPageProcessor = new ActorTemplateListPageProcessor()
	}

	def "returns null when it is not a game performers list"() {
		given:
		Page page = new Page()

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		actorTemplate == null
	}

	def "return null when source page cannot be found"() {
		given:
		Page page = new Page(title: PageName.STAR_TREK_GAME_PERFORMERS)

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		actorTemplate == null
	}

	def "sets page entity from original page wiki page dto"() {
		given:
		Page page = new Page(
				title: PageName.STAR_TREK_GAME_PERFORMERS,
				redirectPath: Lists.newArrayList(PageHeader.builder()
						.title(TITLE)
						.pageId(PAGE_ID)
						.build()))

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		actorTemplate.page.title == TITLE
		actorTemplate.page.pageId == PAGE_ID
	}

	def "sets name from original page wiki page dto"() {
		given:
		Page page = new Page(title: PageName.STAR_TREK_GAME_PERFORMERS,
				redirectPath: Lists.newArrayList(PageHeader.builder()
						.title(TITLE)
						.build()))

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		actorTemplate.name == TITLE
	}

	def "should only set videoGamePerformer flag"() {
		given:
		Page page = new Page(title: PageName.STAR_TREK_GAME_PERFORMERS,
				redirectPath: Lists.newArrayList(PageHeader.builder()
						.title(TITLE)
						.build()))

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

}
