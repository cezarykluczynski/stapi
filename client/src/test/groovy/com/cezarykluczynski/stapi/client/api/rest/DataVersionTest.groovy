package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.DataVersionApi
import spock.lang.Specification

class DataVersionTest extends Specification {

	private DataVersionApi dataVersionApiMock

	private DataVersion dataVersion

	void setup() {
		dataVersionApiMock = Mock()
		dataVersion = new DataVersion(dataVersionApiMock)
	}

	void "gets data version"() {
		given:
		com.cezarykluczynski.stapi.client.v1.rest.model.DataVersion dataVersionResponse = Mock()

		when:
		com.cezarykluczynski.stapi.client.v1.rest.model.DataVersion dataVersionOutput = dataVersion.getDataVersion()

		then:
		1 * dataVersionApiMock.v1RestCommonDataVersionGet() >> dataVersionResponse
		0 * _
		dataVersionOutput == dataVersionResponse
	}

}