package com.cezarykluczynski.stapi.server.technology.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullResponse
import com.cezarykluczynski.stapi.server.technology.reader.TechnologySoapReader
import spock.lang.Specification

class TechnologySoapEndpointTest extends Specification {

	private TechnologySoapReader technologySoapReaderMock

	private TechnologySoapEndpoint technologySoapEndpoint

	void setup() {
		technologySoapReaderMock = Mock()
		technologySoapEndpoint = new TechnologySoapEndpoint(technologySoapReaderMock)
	}

	void "passes base call to TechnologySoapReader"() {
		given:
		TechnologyBaseRequest technologyRequest = Mock()
		TechnologyBaseResponse technologyResponse = Mock()

		when:
		TechnologyBaseResponse technologyResponseResult = technologySoapEndpoint.getTechnologyBase(technologyRequest)

		then:
		1 * technologySoapReaderMock.readBase(technologyRequest) >> technologyResponse
		technologyResponseResult == technologyResponse
	}

	void "passes full call to TechnologySoapReader"() {
		given:
		TechnologyFullRequest technologyFullRequest = Mock()
		TechnologyFullResponse technologyFullResponse = Mock()

		when:
		TechnologyFullResponse technologyResponseResult = technologySoapEndpoint.getTechnologyFull(technologyFullRequest)

		then:
		1 * technologySoapReaderMock.readFull(technologyFullRequest) >> technologyFullResponse
		technologyResponseResult == technologyFullResponse
	}

}
