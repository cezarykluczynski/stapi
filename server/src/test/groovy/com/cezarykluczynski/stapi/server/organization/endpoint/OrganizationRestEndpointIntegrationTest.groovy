package com.cezarykluczynski.stapi.server.organization.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ORGANIZATIONS)
})
class OrganizationRestEndpointIntegrationTest extends AbstractOrganizationEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets organization by UID"() {
		when:
		OrganizationFullResponse organizationFullResponse = stapiRestClient.organizationApi.organizationGet('ORMA0000004225', null)

		then:
		organizationFullResponse.organization.name == 'Vulcan High Command'
	}

	void "Orion Union is among governments from alternate reality"() {
		when:
		OrganizationBaseResponse organizationBaseResponse = stapiRestClient.organizationApi.organizationSearchPost(null, null, null, null,  null,
				true, null, null, null, null, null, null, null, null, null, null, true)

		then:
		organizationBaseResponse.organizations
				.stream()
				.anyMatch { it -> it.name == 'Orion Union' }
	}

}
