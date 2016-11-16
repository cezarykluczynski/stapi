package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class IndividualActorLinkingProcessorTest extends Specification {

	private static final String VALUE = 'VALUE'
	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'
	private static final String TITLE_4 = 'TITLE_4'
	private static final String TITLE_UNKNOWN = 'Unknown actor'
	private static final Long ID_1 = 1L
	private static final Long ID_2 = 2L

	private WikitextApi wikitextApiMock

	private PerformerRepository performerRepositoryMock

	private IndividualActorLinkingProcessor individualActorLinkingProcessor

	def setup() {
		wikitextApiMock = Mock(WikitextApi)
		performerRepositoryMock = Mock(PerformerRepository)
		individualActorLinkingProcessor = new IndividualActorLinkingProcessor(wikitextApiMock, performerRepositoryMock)
	}

	def "should do nothing if either template part or individual template is null"() {
		when: 'Template.Part is null'
		individualActorLinkingProcessor.enrich(EnrichablePair.of(null, new IndividualTemplate()))

		then: 'there is no interactions with dependencies'
		0 * _

		when: 'Template.Part is null'
		individualActorLinkingProcessor.enrich(EnrichablePair.of(new Template.Part(), null))

		then: 'there is no interactions with dependencies'
		0 * _
	}

	def "found performers should be added"() {
		given:
		Template.Part templatePart = new Template.Part(
				value: VALUE
		)
		Set<Performer> performerList = Sets.newHashSet()
		IndividualTemplate individualTemplate = Mock(IndividualTemplate)
		PageLink pageLink1 = new PageLink(title: TITLE_1)
		PageLink pageLink2 = new PageLink(title: TITLE_2)
		PageLink pageLink3 = Mock(PageLink)
		PageLink pageLink4 = new PageLink(title: TITLE_4)
		PageLink pageLink5 = new PageLink(title: TITLE_UNKNOWN)

		Performer performer1 = new Performer(id: ID_1)
		Performer performer2 = new Performer(id: ID_2)
		Performer performer4 = new Performer(id: ID_2)

		when:
		individualActorLinkingProcessor.enrich(EnrichablePair.of(templatePart, individualTemplate))

		then: 'page links are found'
		1 * wikitextApiMock.getPageLinksFromWikitext(VALUE) >> Lists.newArrayList(
				pageLink1, pageLink2, pageLink3, pageLink4, pageLink5)

		then: 'performers are found by page titles'
		1 * performerRepositoryMock.findByPageTitle(TITLE_1) >> Optional.of(performer1)
		1 * performerRepositoryMock.findByPageTitle(TITLE_2) >> Optional.of(performer2)
		1 * pageLink3.getTitle() >> TITLE_3
		1 * performerRepositoryMock.findByPageTitle(TITLE_3) >> Optional.empty()

		then: 'missing page title and individual name is used for logging'
		1 * pageLink3.getTitle() >> TITLE_3
		1 * individualTemplate.getName()

		then: 'remaining page is found'
		1 * performerRepositoryMock.findByPageTitle(TITLE_4) >> Optional.of(performer4)

		then: 'list of performers consist of two unique performers'
		1 * individualTemplate.getPerformers() >> performerList
		performerList.size() == 2
		performerList.contains performer1
		performerList.contains performer2

		then: 'no other interactions are expected'
		0 * _
	}

}
