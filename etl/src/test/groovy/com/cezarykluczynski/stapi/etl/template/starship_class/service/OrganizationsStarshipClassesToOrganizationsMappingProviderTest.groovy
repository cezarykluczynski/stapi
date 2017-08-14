package com.cezarykluczynski.stapi.etl.template.starship_class.service

import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository
import spock.lang.Specification

class OrganizationsStarshipClassesToOrganizationsMappingProviderTest extends Specification {

	private static final String INVALID_KEY = 'INVALID_KEY'
	private static final String VALID_KEY = 'Dominion_starship_classes'
	private static final String VALID_NAME = 'Dominion'

	private OrganizationRepository organizationRepositoryMock

	private OrganizationsStarshipClassesToOrganizationsMappingProvider organizationsStarshipClassesToOrganizationsMappingProvider

	void setup() {
		organizationRepositoryMock = Mock()
		organizationsStarshipClassesToOrganizationsMappingProvider = new OrganizationsStarshipClassesToOrganizationsMappingProvider(
				organizationRepositoryMock)
	}

	void "when no mappings is found, empty optional is returned"() {
		when:
		Optional<Organization> organizationsOptional = organizationsStarshipClassesToOrganizationsMappingProvider.provide(INVALID_KEY)

		then:
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

}
