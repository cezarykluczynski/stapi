package com.cezarykluczynski.stapi.server.conflict.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBase
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_CONFLICTS)
})
class ConflictSoapEndpointIntegrationTest extends AbstractConflictEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets conflict  by UID"() {
		when:
		ConflictFullResponse conflictFullResponse = stapiSoapClient.conflictPortType.getConflictFull(new ConflictFullRequest(
				uid: 'CFMA0000091294'
		))

		then:
		conflictFullResponse.conflict.name == 'Temporal War'
	}

	void "gets conflict by name"() {
		when:
		ConflictBaseResponse conflictBaseResponse = stapiSoapClient.conflictPortType.getConflictBase(new ConflictBaseRequest(
				name: 'World War III'
		))
		List<ConflictBase> conflictBaseList = conflictBaseResponse.conflicts

		then:
		conflictBaseList.size() == 1
		conflictBaseList[0].uid == 'CFMA0000045723'
	}

}
