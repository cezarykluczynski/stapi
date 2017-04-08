package com.cezarykluczynski.stapi.server.organization.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullResponse
import com.cezarykluczynski.stapi.server.organization.reader.OrganizationSoapReader
import spock.lang.Specification

class OrganizationSoapEndpointTest extends Specification {

	private OrganizationSoapReader organizationSoapReaderMock

	private OrganizationSoapEndpoint organizationSoapEndpoint

	void setup() {
		organizationSoapReaderMock = Mock()
		organizationSoapEndpoint = new OrganizationSoapEndpoint(organizationSoapReaderMock)
	}

	void "passes base call to OrganizationSoapReader"() {
		given:
		OrganizationBaseRequest organizationRequest = Mock()
		OrganizationBaseResponse organizationResponse = Mock()

		when:
		OrganizationBaseResponse organizationResponseResult = organizationSoapEndpoint.getOrganizationBase(organizationRequest)

		then:
		1 * organizationSoapReaderMock.readBase(organizationRequest) >> organizationResponse
		organizationResponseResult == organizationResponse
	}

	void "passes full call to OrganizationSoapReader"() {
		given:
		OrganizationFullRequest organizationFullRequest = Mock()
		OrganizationFullResponse organizationFullResponse = Mock()

		when:
		OrganizationFullResponse organizationResponseResult = organizationSoapEndpoint.getOrganizationFull(organizationFullRequest)

		then:
		1 * organizationSoapReaderMock.readFull(organizationFullRequest) >> organizationFullResponse
		organizationResponseResult == organizationFullResponse
	}

}
