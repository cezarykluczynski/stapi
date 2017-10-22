package com.cezarykluczynski.stapi.server.occupation.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullResponse
import com.cezarykluczynski.stapi.server.occupation.reader.OccupationSoapReader
import spock.lang.Specification

class OccupationSoapEndpointTest extends Specification {

	private OccupationSoapReader occupationSoapReaderMock

	private OccupationSoapEndpoint occupationSoapEndpoint

	void setup() {
		occupationSoapReaderMock = Mock()
		occupationSoapEndpoint = new OccupationSoapEndpoint(occupationSoapReaderMock)
	}

	void "passes base call to OccupationSoapReader"() {
		given:
		OccupationBaseRequest occupationRequest = Mock()
		OccupationBaseResponse occupationResponse = Mock()

		when:
		OccupationBaseResponse occupationResponseResult = occupationSoapEndpoint.getOccupationBase(occupationRequest)

		then:
		1 * occupationSoapReaderMock.readBase(occupationRequest) >> occupationResponse
		occupationResponseResult == occupationResponse
	}

	void "passes full call to OccupationSoapReader"() {
		given:
		OccupationFullRequest occupationFullRequest = Mock()
		OccupationFullResponse occupationFullResponse = Mock()

		when:
		OccupationFullResponse occupationResponseResult = occupationSoapEndpoint.getOccupationFull(occupationFullRequest)

		then:
		1 * occupationSoapReaderMock.readFull(occupationFullRequest) >> occupationFullResponse
		occupationResponseResult == occupationFullResponse
	}

}
