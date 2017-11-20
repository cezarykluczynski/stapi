package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.LocationPortType
import spock.lang.Specification

class LocationTest extends Specification {

	private LocationPortType locationPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Location location

	void setup() {
		locationPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		location = new Location(locationPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		LocationBaseRequest locationBaseRequest = Mock()
		LocationBaseResponse locationBaseResponse = Mock()

		when:
		LocationBaseResponse locationBaseResponseOutput = location.search(locationBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(locationBaseRequest)
		1 * locationPortTypeMock.getLocationBase(locationBaseRequest) >> locationBaseResponse
		0 * _
		locationBaseResponse == locationBaseResponseOutput
	}

	void "searches entities"() {
		given:
		LocationFullRequest locationFullRequest = Mock()
		LocationFullResponse locationFullResponse = Mock()

		when:
		LocationFullResponse locationFullResponseOutput = location.get(locationFullRequest)

		then:
		1 * apiKeySupplierMock.supply(locationFullRequest)
		1 * locationPortTypeMock.getLocationFull(locationFullRequest) >> locationFullResponse
		0 * _
		locationFullResponse == locationFullResponseOutput
	}

}
