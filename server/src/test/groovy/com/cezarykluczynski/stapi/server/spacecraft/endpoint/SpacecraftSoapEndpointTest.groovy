package com.cezarykluczynski.stapi.server.spacecraft.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullResponse
import com.cezarykluczynski.stapi.server.spacecraft.reader.SpacecraftSoapReader
import spock.lang.Specification

class SpacecraftSoapEndpointTest extends Specification {

	private SpacecraftSoapReader spacecraftSoapReaderMock

	private SpacecraftSoapEndpoint spacecraftSoapEndpoint

	void setup() {
		spacecraftSoapReaderMock = Mock()
		spacecraftSoapEndpoint = new SpacecraftSoapEndpoint(spacecraftSoapReaderMock)
	}

	void "passes base call to SpacecraftSoapReader"() {
		given:
		SpacecraftBaseRequest spacecraftBaseRequest = Mock()
		SpacecraftBaseResponse spacecraftBaseResponse = Mock()

		when:
		SpacecraftBaseResponse spacecraftResponseResult = spacecraftSoapEndpoint.getSpacecraftBase(spacecraftBaseRequest)

		then:
		1 * spacecraftSoapReaderMock.readBase(spacecraftBaseRequest) >> spacecraftBaseResponse
		spacecraftResponseResult == spacecraftBaseResponse
	}

	void "passes full call to SpacecraftSoapReader"() {
		given:
		SpacecraftFullRequest spacecraftFullRequest = Mock()
		SpacecraftFullResponse spacecraftFullResponse = Mock()

		when:
		SpacecraftFullResponse spacecraftResponseResult = spacecraftSoapEndpoint.getSpacecraftFull(spacecraftFullRequest)

		then:
		1 * spacecraftSoapReaderMock.readFull(spacecraftFullRequest) >> spacecraftFullResponse
		spacecraftResponseResult == spacecraftFullResponse
	}

}
