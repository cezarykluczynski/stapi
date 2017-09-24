package com.cezarykluczynski.stapi.server.material.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullResponse
import com.cezarykluczynski.stapi.server.material.reader.MaterialSoapReader
import spock.lang.Specification

class MaterialSoapEndpointTest extends Specification {

	private MaterialSoapReader materialSoapReaderMock

	private MaterialSoapEndpoint materialSoapEndpoint

	void setup() {
		materialSoapReaderMock = Mock()
		materialSoapEndpoint = new MaterialSoapEndpoint(materialSoapReaderMock)
	}

	void "passes base call to MaterialSoapReader"() {
		given:
		MaterialBaseRequest materialRequest = Mock()
		MaterialBaseResponse materialResponse = Mock()

		when:
		MaterialBaseResponse materialResponseResult = materialSoapEndpoint.getMaterialBase(materialRequest)

		then:
		1 * materialSoapReaderMock.readBase(materialRequest) >> materialResponse
		materialResponseResult == materialResponse
	}

	void "passes full call to MaterialSoapReader"() {
		given:
		MaterialFullRequest materialFullRequest = Mock()
		MaterialFullResponse materialFullResponse = Mock()

		when:
		MaterialFullResponse materialResponseResult = materialSoapEndpoint.getMaterialFull(materialFullRequest)

		then:
		1 * materialSoapReaderMock.readFull(materialFullRequest) >> materialFullResponse
		materialResponseResult == materialFullResponse
	}

}
