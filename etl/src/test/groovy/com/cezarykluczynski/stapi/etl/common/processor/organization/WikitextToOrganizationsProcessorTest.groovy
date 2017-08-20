package com.cezarykluczynski.stapi.etl.common.processor.organization

import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToOrganizationsProcessorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String LINK_TITLE_1 = 'LINK_TITLE_1'
	private static final String LINK_TITLE_2 = 'LINK_TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private OrganizationRepository organizationRepositoryMock

	private WikitextToOrganizationsProcessor wikitextToOrganizationsProcessor

	void setup() {
		wikitextApiMock = Mock()
		organizationRepositoryMock = Mock()
		wikitextToOrganizationsProcessor = new WikitextToOrganizationsProcessor(wikitextApiMock, organizationRepositoryMock)
	}

	void "given wikitext, gets links from it, then returns companies that have associated pages with matching titles"() {
		given:
		Organization organization = Mock()

		when:
		Set<Organization> organizationSet = wikitextToOrganizationsProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * organizationRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.of(organization)
		1 * organizationRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		organizationSet.size() == 1
		organizationSet.contains organization
	}

}
