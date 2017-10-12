package com.cezarykluczynski.stapi.server.conflict.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_CONFLICTS)
})
class ConflictRestEndpointIntegrationTest extends AbstractConflictEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets conflict by UID"() {
		when:
		ConflictFullResponse conflictFullResponse = stapiRestClient.conflictApi.conflictGet('CFMA0000175737', null)

		then:
		conflictFullResponse.conflict.name == 'Mudd Incident'
	}

	void "'Dominion War' is among wars fought by both Federation and the Klingons"() {
		when:
		ConflictBaseResponse conflictBaseResponse = stapiRestClient.conflictApi.conflictSearchPost(null, null, null, null, null, null, null, null,
				true, true, null, null)

		then:
		conflictBaseResponse.conflicts.stream()
				.anyMatch { it.name == 'Dominion War' }
	}

}
