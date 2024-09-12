package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.common.service.ExternalLinkFilter
import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink
import spock.lang.Specification

class ActorTemplateExternalLinksEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock
	private ExternalLinkFilter externalLinkFilterMock
	private ActorTemplateExternalLinksEnrichingProcessor actorTemplateExternalLinksEnrichingProcessor

	void setup() {
		wikitextApiMock = Mock()
		externalLinkFilterMock = Mock()
		actorTemplateExternalLinksEnrichingProcessor = new ActorTemplateExternalLinksEnrichingProcessor(wikitextApiMock, externalLinkFilterMock)
	}

	void "does nothing when there is no external links section"() {
		Page page = new Page()
		ActorTemplate actorTemplate = new ActorTemplate()
		given:
		EnrichablePair<Page, ActorTemplate> enrichablePair = EnrichablePair.of(page, actorTemplate)

		when:
		actorTemplateExternalLinksEnrichingProcessor.enrich(enrichablePair)

		then:
		0 * _
	}

	void "does nothing when there is external links section, but badly named"() {
		PageSection pageSection = new PageSection(anchor: 'ExternalLinks')
		Page page = new Page(sections: [pageSection])
		ActorTemplate actorTemplate = new ActorTemplate()
		given:
		EnrichablePair<Page, ActorTemplate> enrichablePair = EnrichablePair.of(page, actorTemplate)

		when:
		actorTemplateExternalLinksEnrichingProcessor.enrich(enrichablePair)

		then:
		0 * _
	}

	void "enriches with links from external links section"() {
		PageSection pageSection = new PageSection(anchor: 'External_links', wikitext: WIKITEXT)
		ExternalLink externalLink1 = Mock()
		ExternalLink externalLink2 = Mock()
		ExternalLink externalLinkToFilterOut = Mock()
		List<ExternalLink> externalLinks = [externalLink1, externalLink2, externalLinkToFilterOut]
		Page page = new Page(sections: [pageSection])
		ActorTemplate actorTemplate = new ActorTemplate()
		given:
		EnrichablePair<Page, ActorTemplate> enrichablePair = EnrichablePair.of(page, actorTemplate)

		when:
		actorTemplateExternalLinksEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * wikitextApiMock.getExternalLinksFromWikitext(WIKITEXT, page) >> externalLinks
		1 * externalLinkFilterMock.isPersonLink(externalLink1) >> true
		1 * externalLinkFilterMock.isPersonLink(externalLink2) >> true
		1 * externalLinkFilterMock.isPersonLink(externalLinkToFilterOut) >> false
		0 * _
		actorTemplate.externalLinks == Set.of(externalLink1, externalLink2)
	}

}
