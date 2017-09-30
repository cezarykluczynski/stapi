package com.cezarykluczynski.stapi.etl.template.character.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class CharacterTemplateActorLinkingEnrichingProcessorTest extends Specification {

	private static final String VALUE = 'VALUE'
	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'
	private static final String TITLE_4 = 'TITLE_4'
	private static final String TITLE_UNKNOWN = 'Unknown actor'
	private static final Long ID_1 = 1L
	private static final Long ID_2 = 2L
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private WikitextApi wikitextApiMock

	private PerformerRepository performerRepositoryMock

	private CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessorMock

	void setup() {
		wikitextApiMock = Mock()
		performerRepositoryMock = Mock()
		characterTemplateActorLinkingEnrichingProcessorMock = new CharacterTemplateActorLinkingEnrichingProcessor(wikitextApiMock,
				performerRepositoryMock)
	}

	void "should do nothing if either value or individual template is null"() {
		when: 'Template.Part is null'
		characterTemplateActorLinkingEnrichingProcessorMock.enrich(EnrichablePair.of(null, new CharacterTemplate()))

		then: 'there is no interactions with dependencies'
		0 * _

		when: 'Template.Part is null'
		characterTemplateActorLinkingEnrichingProcessorMock.enrich(EnrichablePair.of(null, null))

		then: 'there is no interactions with dependencies'
		0 * _
	}

	void "found performers should be added"() {
		given:
		Set<Performer> performerList = Sets.newHashSet()
		CharacterTemplate characterTemplate = Mock()
		PageLink pageLink1 = new PageLink(title: TITLE_1)
		PageLink pageLink2 = new PageLink(title: TITLE_2)
		PageLink pageLink3 = Mock()
		PageLink pageLink4 = new PageLink(title: TITLE_4)
		PageLink pageLink5 = new PageLink(title: TITLE_UNKNOWN)

		Performer performer1 = new Performer(id: ID_1)
		Performer performer2 = new Performer(id: ID_2)
		Performer performer4 = new Performer(id: ID_2)

		when:
		characterTemplateActorLinkingEnrichingProcessorMock.enrich(EnrichablePair.of(VALUE, characterTemplate))

		then: 'page links are found'
		1 * wikitextApiMock.getPageLinksFromWikitext(VALUE) >> Lists.newArrayList(
				pageLink1, pageLink2, pageLink3, pageLink4, pageLink5)

		then: 'performers are found by page titles'
		1 * performerRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE_1, SOURCE) >> Optional.of(performer1)
		1 * performerRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE_2, SOURCE) >> Optional.of(performer2)
		1 * pageLink3.title >> TITLE_3
		1 * performerRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE_3, SOURCE) >> Optional.empty()

		then: 'missing page title and individual name is used for logging'
		1 * pageLink3.title >> TITLE_3
		1 * characterTemplate.name

		then: 'remaining page is found'
		1 * performerRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE_4, SOURCE) >> Optional.of(performer4)

		then: 'list of performers consist of two unique performers'
		1 * characterTemplate.performers >> performerList
		performerList.size() == 2
		performerList.contains performer1
		performerList.contains performer2

		then: 'no other interactions are expected'
		0 * _
	}

}
