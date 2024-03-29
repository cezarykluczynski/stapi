package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.OrganizationApi
import com.cezarykluczynski.stapi.client.rest.model.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.OrganizationFullResponse
import com.cezarykluczynski.stapi.client.rest.model.OrganizationSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractOrganizationTest

class OrganizationTest extends AbstractOrganizationTest {

	private OrganizationApi organizationApiMock

	private Organization organization

	void setup() {
		organizationApiMock = Mock()
		organization = new Organization(organizationApiMock)
	}

	void "gets single entity"() {
		given:
		OrganizationFullResponse organizationFullResponse = Mock()

		when:
		OrganizationFullResponse organizationFullResponseOutput = organization.get(UID)

		then:
		1 * organizationApiMock.v1GetOrganization(UID) >> organizationFullResponse
		0 * _
		organizationFullResponse == organizationFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		OrganizationBaseResponse organizationBaseResponse = Mock()
		OrganizationSearchCriteria organizationSearchCriteria = new OrganizationSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				government: GOVERNMENT,
				intergovernmentalOrganization: INTERGOVERNMENTAL_ORGANIZATION,
				researchOrganization: RESEARCH_ORGANIZATION,
				sportOrganization: SPORT_ORGANIZATION,
				medicalOrganization: MEDICAL_ORGANIZATION,
				militaryOrganization: MILITARY_ORGANIZATION,
				militaryUnit: MILITARY_UNIT,
				governmentAgency: GOVERNMENT_AGENCY,
				lawEnforcementAgency: LAW_ENFORCEMENT_AGENCY,
				prisonOrPenalColony: PRISON_OR_PENAL_COLONY,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)
		organizationSearchCriteria.sort = SORT

		when:
		OrganizationBaseResponse organizationBaseResponseOutput = organization.search(organizationSearchCriteria)

		then:
		1 * organizationApiMock.v1SearchOrganizations(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, GOVERNMENT,
				INTERGOVERNMENTAL_ORGANIZATION, RESEARCH_ORGANIZATION, SPORT_ORGANIZATION, MEDICAL_ORGANIZATION, MILITARY_ORGANIZATION, MILITARY_UNIT,
				GOVERNMENT_AGENCY, LAW_ENFORCEMENT_AGENCY, PRISON_OR_PENAL_COLONY, MIRROR, ALTERNATE_REALITY) >> organizationBaseResponse
		0 * _
		organizationBaseResponse == organizationBaseResponseOutput
	}

}
