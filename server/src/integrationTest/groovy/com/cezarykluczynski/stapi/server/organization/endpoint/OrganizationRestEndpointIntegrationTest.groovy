package com.cezarykluczynski.stapi.server.organization.endpoint

import com.cezarykluczynski.stapi.client.api.dto.OrganizationSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ORGANIZATIONS)
})
class OrganizationRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets organization by UID"() {
		when:
		OrganizationFullResponse organizationFullResponse = stapiRestClient.organization.get('ORMA0000004225')

		then:
		organizationFullResponse.organization.name == 'Vulcan High Command'
	}

	void "Orion Union is among governments from alternate reality"() {
		given:
		OrganizationSearchCriteria organizationSearchCriteria = new OrganizationSearchCriteria(
				alternateReality: true
		)

		when:
		OrganizationBaseResponse organizationBaseResponse = stapiRestClient.organization.search(organizationSearchCriteria)

		then:
		organizationBaseResponse.organizations
				.stream()
				.anyMatch { it -> it.name == 'Orion Union' }
	}

}
