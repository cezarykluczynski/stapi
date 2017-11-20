package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationPortType
import spock.lang.Specification

class OrganizationTest extends Specification {

	private OrganizationPortType organizationPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Organization organization

	void setup() {
		organizationPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		organization = new Organization(organizationPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		OrganizationBaseRequest organizationBaseRequest = Mock()
		OrganizationBaseResponse organizationBaseResponse = Mock()

		when:
		OrganizationBaseResponse organizationBaseResponseOutput = organization.search(organizationBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(organizationBaseRequest)
		1 * organizationPortTypeMock.getOrganizationBase(organizationBaseRequest) >> organizationBaseResponse
		0 * _
		organizationBaseResponse == organizationBaseResponseOutput
	}

	void "searches entities"() {
		given:
		OrganizationFullRequest organizationFullRequest = Mock()
		OrganizationFullResponse organizationFullResponse = Mock()

		when:
		OrganizationFullResponse organizationFullResponseOutput = organization.get(organizationFullRequest)

		then:
		1 * apiKeySupplierMock.supply(organizationFullRequest)
		1 * organizationPortTypeMock.getOrganizationFull(organizationFullRequest) >> organizationFullResponse
		0 * _
		organizationFullResponse == organizationFullResponseOutput
	}

}
