package com.cezarykluczynski.stapi.server.element.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullResponse
import com.cezarykluczynski.stapi.server.element.reader.ElementSoapReader
import spock.lang.Specification

class ElementSoapEndpointTest extends Specification {

	private ElementSoapReader elementSoapReaderMock

	private ElementSoapEndpoint elementSoapEndpoint

	void setup() {
		elementSoapReaderMock = Mock()
		elementSoapEndpoint = new ElementSoapEndpoint(elementSoapReaderMock)
	}

	void "passes base call to ElementSoapReader"() {
		given:
		ElementBaseRequest elementRequest = Mock()
		ElementBaseResponse elementResponse = Mock()

		when:
		ElementBaseResponse elementResponseResult = elementSoapEndpoint.getElementBase(elementRequest)

		then:
		1 * elementSoapReaderMock.readBase(elementRequest) >> elementResponse
		elementResponseResult == elementResponse
	}

	void "passes full call to ElementSoapReader"() {
		given:
		ElementFullRequest elementFullRequest = Mock()
		ElementFullResponse elementFullResponse = Mock()

		when:
		ElementFullResponse elementResponseResult = elementSoapEndpoint.getElementFull(elementFullRequest)

		then:
		1 * elementSoapReaderMock.readFull(elementFullRequest) >> elementFullResponse
		elementResponseResult == elementFullResponse
	}

}
