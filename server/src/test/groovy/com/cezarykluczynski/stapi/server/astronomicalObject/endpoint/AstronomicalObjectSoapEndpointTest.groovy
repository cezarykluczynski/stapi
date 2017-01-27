package com.cezarykluczynski.stapi.server.astronomicalObject.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectResponse
import com.cezarykluczynski.stapi.server.astronomicalObject.reader.AstronomicalObjectSoapReader
import spock.lang.Specification

class AstronomicalObjectSoapEndpointTest extends Specification {

	private AstronomicalObjectSoapReader astronomicalObjectSoapReaderMock

	private AstronomicalObjectSoapEndpoint astronomicalObjectSoapEndpoint

	void setup() {
		astronomicalObjectSoapReaderMock = Mock(AstronomicalObjectSoapReader)
		astronomicalObjectSoapEndpoint = new AstronomicalObjectSoapEndpoint(astronomicalObjectSoapReaderMock)
	}

	void "passes call to AstronomicalObjectSoapReader"() {
		given:
		AstronomicalObjectRequest astronomicalObjectRequest = Mock(AstronomicalObjectRequest)
		AstronomicalObjectResponse astronomicalObjectResponse = Mock(AstronomicalObjectResponse)

		when:
		AstronomicalObjectResponse astronomicalObjectResponseResult = astronomicalObjectSoapEndpoint.getAstronomicalObjects(astronomicalObjectRequest)

		then:
		1 * astronomicalObjectSoapReaderMock.read(astronomicalObjectRequest) >> astronomicalObjectResponse
		astronomicalObjectResponseResult == astronomicalObjectResponse
	}

}
