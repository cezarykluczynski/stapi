package com.cezarykluczynski.stapi.etl.common.processor.company

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToCompaniesProcessorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String LINK_TITLE_1 = 'LINK_TITLE_1'
	private static final String LINK_TITLE_2 = 'LINK_TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private CompanyRepository companyRepositoryMock

	private WikitextToCompaniesProcessor wikitextToCompaniesProcessor

	void setup() {
		wikitextApiMock = Mock()
		companyRepositoryMock = Mock()
		wikitextToCompaniesProcessor = new WikitextToCompaniesProcessor(wikitextApiMock, companyRepositoryMock)
	}

	void "given wikitext, gets links from it, then returns companies that have associated pages with matching titles"() {
		given:
		Company company = Mock()

		when:
		Set<Company> companySet = wikitextToCompaniesProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * companyRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.of(company)
		1 * companyRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		companySet.size() == 1
		companySet.contains company
	}

}
