package com.cezarykluczynski.stapi.server.location.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullResponse
import com.cezarykluczynski.stapi.server.location.reader.LocationSoapReader
import spock.lang.Specification

class LocationSoapEndpointTest extends Specification {

	private LocationSoapReader locationSoapReaderMock

	private LocationSoapEndpoint locationSoapEndpoint

	void setup() {
		locationSoapReaderMock = Mock()
		locationSoapEndpoint = new LocationSoapEndpoint(locationSoapReaderMock)
	}

	void "passes base call to LocationSoapReader"() {
		given:
		LocationBaseRequest locationRequest = Mock()
		LocationBaseResponse locationResponse = Mock()

		when:
		LocationBaseResponse locationResponseResult = locationSoapEndpoint.getLocationBase(locationRequest)

		then:
		1 * locationSoapReaderMock.readBase(locationRequest) >> locationResponse
		locationResponseResult == locationResponse
	}

	void "passes full call to LocationSoapReader"() {
		given:
		LocationFullRequest locationFullRequest = Mock()
		LocationFullResponse locationFullResponse = Mock()

		when:
		LocationFullResponse locationResponseResult = locationSoapEndpoint.getLocationFull(locationFullRequest)

		then:
		1 * locationSoapReaderMock.readFull(locationFullRequest) >> locationFullResponse
		locationResponseResult == locationFullResponse
	}

}
