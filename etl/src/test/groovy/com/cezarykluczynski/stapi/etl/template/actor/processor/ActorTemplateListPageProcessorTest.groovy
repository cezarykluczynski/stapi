package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.constant.PageName
import com.google.common.collect.Lists
import spock.lang.Specification

class ActorTemplateListPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Long PAGE_ID = 1L
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private PageBindingService pageBindingServiceMock

	private ActorTemplateListPageProcessor actorTemplateListPageProcessor

	void setup() {
		pageBindingServiceMock = Mock(PageBindingService)
		actorTemplateListPageProcessor = new ActorTemplateListPageProcessor(pageBindingServiceMock)
	}

	void "returns null when it is not a game performers list"() {
		given:
		Page page = new Page()

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		0 * pageBindingServiceMock.fromPageHeaderToPageEntity(_)
		actorTemplate == null
	}

	void "return null when source page cannot be found"() {
		given:
		Page page = new Page(title: PageName.STAR_TREK_GAME_PERFORMERS)

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		0 * pageBindingServiceMock.fromPageHeaderToPageEntity(_)
		actorTemplate == null
	}

	void "sets page entity from original page wiki page dto"() {
		given:
		Page page = new Page(
				title: PageName.STAR_TREK_GAME_PERFORMERS,
				redirectPath: Lists.newArrayList(PageHeader.builder()
						.title(TITLE)
						.pageId(PAGE_ID)
						.mediaWikiSource(SOURCES_MEDIA_WIKI_SOURCE)
						.build()))
		PageEntity pageEntity = Mock(PageEntity)

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageHeaderToPageEntity(_ as PageHeader) >> { PageHeader pageHeader ->
			assert pageHeader.title == TITLE
			assert pageHeader.pageId == PAGE_ID
			assert pageHeader.mediaWikiSource == SOURCES_MEDIA_WIKI_SOURCE
			pageEntity
		}
		actorTemplate.page == pageEntity
	}

	void "sets name from original page wiki page dto"() {
		given:
		Page page = new Page(title: PageName.STAR_TREK_GAME_PERFORMERS,
				redirectPath: Lists.newArrayList(PageHeader.builder()
						.title(TITLE)
						.build()))

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageHeaderToPageEntity(_)
		actorTemplate.name == TITLE
	}

	void "should only set videoGamePerformer flag"() {
		given:
		Page page = new Page(title: PageName.STAR_TREK_GAME_PERFORMERS,
				redirectPath: Lists.newArrayList(PageHeader.builder()
						.title(TITLE)
						.build()))

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageHeaderToPageEntity(_)
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
