package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.OrganizationApi
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFullResponse
import com.cezarykluczynski.stapi.util.AbstractOrganizationTest

class OrganizationTest extends AbstractOrganizationTest {

	private OrganizationApi organizationApiMock

	private Organization organization

	void setup() {
		organizationApiMock = Mock()
		organization = new Organization(organizationApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		OrganizationFullResponse organizationFullResponse = Mock()

		when:
		OrganizationFullResponse organizationFullResponseOutput = organization.get(UID)

		then:
		1 * organizationApiMock.organizationGet(UID, API_KEY) >> organizationFullResponse
		0 * _
		organizationFullResponse == organizationFullResponseOutput
	}

	void "searches entities"() {
		given:
		OrganizationBaseResponse organizationBaseResponse = Mock()

		when:
		OrganizationBaseResponse organizationBaseResponseOutput = organization.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, GOVERNMENT,
				INTERGOVERNMENTAL_ORGANIZATION, RESEARCH_ORGANIZATION, SPORT_ORGANIZATION, MEDICAL_ORGANIZATION, MILITARY_ORGANIZATION, MILITARY_UNIT,
				GOVERNMENT_AGENCY, LAW_ENFORCEMENT_AGENCY, PRISON_OR_PENAL_COLONY, MIRROR, ALTERNATE_REALITY)

		then:
		1 * organizationApiMock.organizationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, GOVERNMENT, INTERGOVERNMENTAL_ORGANIZATION,
				RESEARCH_ORGANIZATION, SPORT_ORGANIZATION, MEDICAL_ORGANIZATION, MILITARY_ORGANIZATION, MILITARY_UNIT, GOVERNMENT_AGENCY,
				LAW_ENFORCEMENT_AGENCY, PRISON_OR_PENAL_COLONY, MIRROR, ALTERNATE_REALITY) >> organizationBaseResponse
		0 * _
		organizationBaseResponse == organizationBaseResponseOutput
	}

}
