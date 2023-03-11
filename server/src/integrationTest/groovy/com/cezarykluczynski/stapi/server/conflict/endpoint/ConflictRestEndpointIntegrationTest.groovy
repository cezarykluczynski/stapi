package com.cezarykluczynski.stapi.server.conflict.endpoint

import com.cezarykluczynski.stapi.client.api.dto.ConflictSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ConflictV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_CONFLICTS)
})
class ConflictRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets conflict by UID"() {
		when:
		ConflictV2FullResponse conflictV2FullResponse = stapiRestClient.conflict.getV2('CFMA0000175737')

		then:
		conflictV2FullResponse.conflict.name == 'Mudd Incident'
	}

	void "'Dominion War' is among wars fought by both Federation and the Klingons"() {
		given:
		ConflictSearchCriteria conflictSearchCriteria = new ConflictSearchCriteria(
				federationWar: true,
				klingonWar: true
		)

		when:
		ConflictBaseResponse conflictBaseResponse = stapiRestClient.conflict.search(conflictSearchCriteria)

		then:
		conflictBaseResponse.conflicts.stream()
				.anyMatch { it.name == 'Dominion War' }
	}

}
