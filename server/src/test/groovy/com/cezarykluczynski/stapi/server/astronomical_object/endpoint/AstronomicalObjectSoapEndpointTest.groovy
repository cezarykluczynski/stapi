package com.cezarykluczynski.stapi.server.astronomical_object.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.server.astronomical_object.reader.AstronomicalObjectSoapReader
import spock.lang.Specification

class AstronomicalObjectSoapEndpointTest extends Specification {

	private AstronomicalObjectSoapReader astronomicalObjectSoapReaderMock

	private AstronomicalObjectSoapEndpoint astronomicalObjectSoapEndpoint

	void setup() {
		astronomicalObjectSoapReaderMock = Mock()
		astronomicalObjectSoapEndpoint = new AstronomicalObjectSoapEndpoint(astronomicalObjectSoapReaderMock)
	}

	void "passes base call to AstronomicalObjectSoapReader"() {
		given:
		AstronomicalObjectBaseRequest astronomicalObjectBaseRequest = Mock()
		AstronomicalObjectBaseResponse astronomicalObjectBaseResponse = Mock()

		when:
		AstronomicalObjectBaseResponse astronomicalObjectResponseResult = astronomicalObjectSoapEndpoint
				.getAstronomicalObjectBase(astronomicalObjectBaseRequest)

		then:
		1 * astronomicalObjectSoapReaderMock.readBase(astronomicalObjectBaseRequest) >> astronomicalObjectBaseResponse
		astronomicalObjectResponseResult == astronomicalObjectBaseResponse
	}

	void "passes full call to AstronomicalObjectSoapReader"() {
		given:
		AstronomicalObjectFullRequest astronomicalObjectFullRequest = Mock()
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = Mock()

		when:
		AstronomicalObjectFullResponse astronomicalObjectResponseResult = astronomicalObjectSoapEndpoint
				.getAstronomicalObjectFull(astronomicalObjectFullRequest)

		then:
		1 * astronomicalObjectSoapReaderMock.readFull(astronomicalObjectFullRequest) >> astronomicalObjectFullResponse
		astronomicalObjectResponseResult == astronomicalObjectFullResponse
	}

}
