package com.cezarykluczynski.stapi.etl.template.starship_class.service

import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class OrganizationsStarshipClassesToOrganizationsMappingProviderTest extends Specification {

	private static final String INVALID_KEY = 'INVALID_KEY'
	private static final String VALID_PAGE = 'Cardassian Union'
	private static final Long VALID_PAGE_ID = 456
	private static final String VALID_KEY = 'Dominion_starship_classes'
	private static final String VALID_NAME = 'Dominion'
	private static final String ORGANIZATION_1 = 'ORGANIZATION_1'
	private static final String ORGANIZATION_2 = 'ORGANIZATION_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private PageApi pageApiMock

	private WikitextApi wikitextApiMock

	private OrganizationRepository organizationRepositoryMock

	private OrganizationsStarshipClassesToOrganizationsMappingProvider organizationsStarshipClassesToOrganizationsMappingProvider

	void setup() {
		pageApiMock = Mock()
		wikitextApiMock = Mock()
		organizationRepositoryMock = Mock()
		organizationsStarshipClassesToOrganizationsMappingProvider = new OrganizationsStarshipClassesToOrganizationsMappingProvider(
				pageApiMock, wikitextApiMock, organizationRepositoryMock)
	}

	void "when no mappings is found, and no page is found, empty optional is returned"() {
		when:
		Optional<Organization> organizationsOptional = organizationsStarshipClassesToOrganizationsMappingProvider.provide(INVALID_KEY)

		then:
		1 * pageApiMock.getCategory(INVALID_KEY, MediaWikiSource.MEMORY_ALPHA_EN) >> null
		0 * _
		!organizationsOptional.isPresent()
	}

	void "when mappings is found, result of repository query is returned"() {
		given:
		Organization organization = Mock()
		Optional<Organization> organizationsOptional = Optional.of(organization)

		when:
		Optional<Organization> organizationsOptionalOutput = organizationsStarshipClassesToOrganizationsMappingProvider.provide(VALID_KEY)

		then:
		1 * organizationRepositoryMock.findByName(VALID_NAME) >> organizationsOptional
		0 * _
		organizationsOptionalOutput == organizationsOptional
	}

	void "when no mappings is found, but page is found, result of repository query is returned"() {
		given:
		Organization organization = organizationOf(VALID_PAGE, VALID_PAGE_ID)
		Optional<Organization> organizationOptional = Optional.of(organization)
		Page page = new Page(title: VALID_PAGE, wikitext: WIKITEXT)

		when:
		Optional<Organization> organizationsOptionalOutput = organizationsStarshipClassesToOrganizationsMappingProvider.provide(VALID_PAGE)

		then:
		1 * pageApiMock.getCategory(VALID_PAGE, MediaWikiSource.MEMORY_ALPHA_EN) >> page
		1 * organizationRepositoryMock.findAll() >> [
				organizationOf(ORGANIZATION_1, 45),
				organizationOf(VALID_PAGE, VALID_PAGE_ID),
				organizationOf(ORGANIZATION_2, 56)
		]
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> [new PageLink(title: VALID_PAGE)]
		1 * organizationRepositoryMock.findById(VALID_PAGE_ID) >> Optional.of(organization)
		0 * _
		organizationsOptionalOutput == organizationOptional

		when:
		organizationsOptionalOutput = organizationsStarshipClassesToOrganizationsMappingProvider.provide(VALID_PAGE)

		then:
		1 * organizationRepositoryMock.findById(VALID_PAGE_ID) >> Optional.of(organization)
		0 * _
		organizationsOptionalOutput == organizationOptional
	}

	private static Organization organizationOf(String name, Long id) {
		new Organization(id: id, name: name, page: new com.cezarykluczynski.stapi.model.page.entity.Page(title: name))
	}

}
