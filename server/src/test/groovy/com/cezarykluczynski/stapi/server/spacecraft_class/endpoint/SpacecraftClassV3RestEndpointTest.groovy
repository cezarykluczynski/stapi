package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV3FullResponse
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.spacecraft_class.reader.SpacecraftClassV3RestReader

class SpacecraftClassV3RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'

	private SpacecraftClassV3RestReader spacecraftClassV3RestReaderMock

	private SpacecraftClassV3RestEndpoint spacecraftClassV3RestEndpoint

	void setup() {
		spacecraftClassV3RestReaderMock = Mock()
		spacecraftClassV3RestEndpoint = new SpacecraftClassV3RestEndpoint(spacecraftClassV3RestReaderMock)
	}

	void "passes get call to SpacecraftClassV3RestReader"() {
		given:
		SpacecraftClassV3FullResponse spacecraftClassV3FullResponse = Mock()

		when:
		SpacecraftClassV3FullResponse spacecraftClassV3FullResponseOutput = spacecraftClassV3RestEndpoint.getSpacecraftClass(UID)

		then:
		1 * spacecraftClassV3RestReaderMock.readFull(UID) >> spacecraftClassV3FullResponse
		spacecraftClassV3FullResponseOutput == spacecraftClassV3FullResponse
	}

}
