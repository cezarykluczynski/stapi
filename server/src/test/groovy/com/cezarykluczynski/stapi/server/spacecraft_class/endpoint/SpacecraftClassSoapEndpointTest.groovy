package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullResponse
import com.cezarykluczynski.stapi.server.spacecraft_class.reader.SpacecraftClassSoapReader
import spock.lang.Specification

class SpacecraftClassSoapEndpointTest extends Specification {

	private SpacecraftClassSoapReader spacecraftClassSoapReaderMock

	private SpacecraftClassSoapEndpoint spacecraftClassSoapEndpoint

	void setup() {
		spacecraftClassSoapReaderMock = Mock()
		spacecraftClassSoapEndpoint = new SpacecraftClassSoapEndpoint(spacecraftClassSoapReaderMock)
	}

	void "passes base call to SpacecraftClassSoapReader"() {
		given:
		SpacecraftClassBaseRequest spacecraftClassBaseRequest = Mock()
		SpacecraftClassBaseResponse spacecraftClassBaseResponse = Mock()

		when:
		SpacecraftClassBaseResponse spacecraftClassResponseResult = spacecraftClassSoapEndpoint.getSpacecraftClassBase(spacecraftClassBaseRequest)

		then:
		1 * spacecraftClassSoapReaderMock.readBase(spacecraftClassBaseRequest) >> spacecraftClassBaseResponse
		spacecraftClassResponseResult == spacecraftClassBaseResponse
	}

	void "passes full call to SpacecraftClassSoapReader"() {
		given:
		SpacecraftClassFullRequest spacecraftClassFullRequest = Mock()
		SpacecraftClassFullResponse spacecraftClassFullResponse = Mock()

		when:
		SpacecraftClassFullResponse spacecraftClassResponseResult = spacecraftClassSoapEndpoint.getSpacecraftClassFull(spacecraftClassFullRequest)

		then:
		1 * spacecraftClassSoapReaderMock.readFull(spacecraftClassFullRequest) >> spacecraftClassFullResponse
		spacecraftClassResponseResult == spacecraftClassFullResponse
	}

}
