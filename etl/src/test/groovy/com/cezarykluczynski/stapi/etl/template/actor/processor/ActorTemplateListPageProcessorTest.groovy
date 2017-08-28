package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.actor.dto.LifeRangeDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.constant.PageTitle
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class ActorTemplateListPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Long PAGE_ID = 1L
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1980, 1, 2)
	private static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	private static final LocalDate DATE_OF_DEATH = LocalDate.of(2020, 3, 4)
	private static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'

	private PageBindingService pageBindingServiceMock

	private VideoGamePerformerLifeRangeFixedValueProvider videoGamePerformerLifeRangeFixedValueProviderMock

	private ActorTemplateListPageProcessor actorTemplateListPageProcessor

	void setup() {
		pageBindingServiceMock = Mock()
		videoGamePerformerLifeRangeFixedValueProviderMock = Mock()
		actorTemplateListPageProcessor = new ActorTemplateListPageProcessor(pageBindingServiceMock, videoGamePerformerLifeRangeFixedValueProviderMock)
	}

	void "returns null when it is not a game performers list"() {
		given:
		Page page = new Page()

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		0 * pageBindingServiceMock.fromPageHeaderToPageEntity(_)
		0 * _
		actorTemplate == null
	}

	void "return null when source page cannot be found"() {
		given:
		Page page = new Page(title: PageTitle.STAR_TREK_GAME_PERFORMERS)

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		0 * pageBindingServiceMock.fromPageHeaderToPageEntity(_)
		0 * _
		actorTemplate == null
	}

	void "sets page entity from original page wiki page dto"() {
		given:
		Page page = new Page(
				title: PageTitle.STAR_TREK_GAME_PERFORMERS,
				redirectPath: Lists.newArrayList(new PageHeader(
						title: TITLE,
						pageId: PAGE_ID,
						mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)))
		PageEntity pageEntity = Mock()

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageHeaderToPageEntity(_ as PageHeader) >> { PageHeader pageHeader ->
			assert pageHeader.title == TITLE
			assert pageHeader.pageId == PAGE_ID
			assert pageHeader.mediaWikiSource == SOURCES_MEDIA_WIKI_SOURCE
			pageEntity
		}
		1 * videoGamePerformerLifeRangeFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		0 * _
		actorTemplate.page == pageEntity
	}

	void "sets name from original page wiki page dto"() {
		given:
		Page page = new Page(title: PageTitle.STAR_TREK_GAME_PERFORMERS,
				redirectPath: Lists.newArrayList(new PageHeader(title: TITLE)))

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageHeaderToPageEntity(_)
		1 * videoGamePerformerLifeRangeFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		0 * _
		actorTemplate.name == TITLE
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

	void "sets values from VideoGamePerformerLifeRangeFixedValueProvider, when value is found"() {
		given:
		Page page = new Page(
				title: PageTitle.STAR_TREK_GAME_PERFORMERS,
				redirectPath: Lists.newArrayList(new PageHeader(
						title: TITLE,
						pageId: PAGE_ID,
						mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)))
		PageEntity pageEntity = Mock()
		LifeRangeDTO lifeRangeDTO = LifeRangeDTO.of(DATE_OF_BIRTH, PLACE_OF_BIRTH, DATE_OF_DEATH, PLACE_OF_DEATH)

		when:
		ActorTemplate actorTemplate = actorTemplateListPageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageHeaderToPageEntity(_) >> pageEntity
		1 * videoGamePerformerLifeRangeFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder
				.found(lifeRangeDTO)
		0 * _
		actorTemplate.name == TITLE
		actorTemplate.lifeRange == DateRange.of(DATE_OF_BIRTH, DATE_OF_DEATH)
		actorTemplate.placeOfBirth == PLACE_OF_BIRTH
		actorTemplate.placeOfDeath == PLACE_OF_DEATH
	}

}
