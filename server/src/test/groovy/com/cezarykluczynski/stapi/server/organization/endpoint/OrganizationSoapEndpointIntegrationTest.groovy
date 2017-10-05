package com.cezarykluczynski.stapi.server.organization.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ORGANIZATIONS)
})
class OrganizationSoapEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets organization by UID"() {
		when:
		OrganizationFullResponse organizationFullResponse = stapiSoapClient.organizationPortType.getOrganizationFull(new OrganizationFullRequest(
				uid: 'ORMA0000102839'
		))

		then:
		organizationFullResponse.organization.name == 'United Federation of Planets'
	}

	void "gets organizations with 'Ferengi' in name, that are also medical establishments"() {
		when:
		OrganizationBaseResponse organizationBaseResponse = stapiSoapClient.organizationPortType.getOrganizationBase(new OrganizationBaseRequest(
				name: 'Ferengi',
				medicalOrganization: true
		))

		then:
		organizationBaseResponse.organizations
				.stream()
				.anyMatch { it -> it.name == 'Ferengi Health Commission' }
	}

}
