package com.cezarykluczynski.stapi.server.conflict.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2FullResponse
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.conflict.reader.ConflictV2RestReader

class ConflictV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'

	private ConflictV2RestReader conflictV2RestReaderMock

	private ConflictV2RestEndpoint conflictV2RestEndpoint

	void setup() {
		conflictV2RestReaderMock = Mock()
		conflictV2RestEndpoint = new ConflictV2RestEndpoint(conflictV2RestReaderMock)
	}

	void "passes get call to ConflictRestReader"() {
		given:
		ConflictV2FullResponse conflictV2FullResponse = Mock()

		when:
		ConflictV2FullResponse conflictV2FullResponseOutput = conflictV2RestEndpoint.getConflict(UID)

		then:
		1 * conflictV2RestReaderMock.readFull(UID) >> conflictV2FullResponse
		conflictV2FullResponseOutput == conflictV2FullResponse
	}

}
