package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType
import spock.lang.Specification

class AstronomicalObjectTest extends Specification {

	private AstronomicalObjectPortType astronomicalObjectPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private AstronomicalObject astronomicalObject

	void setup() {
		astronomicalObjectPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		astronomicalObject = new AstronomicalObject(astronomicalObjectPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		AstronomicalObjectBaseRequest astronomicalObjectBaseRequest = Mock()
		AstronomicalObjectBaseResponse astronomicalObjectBaseResponse = Mock()

		when:
		AstronomicalObjectBaseResponse astronomicalObjectBaseResponseOutput = astronomicalObject.search(astronomicalObjectBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(astronomicalObjectBaseRequest)
		1 * astronomicalObjectPortTypeMock.getAstronomicalObjectBase(astronomicalObjectBaseRequest) >> astronomicalObjectBaseResponse
		0 * _
		astronomicalObjectBaseResponse == astronomicalObjectBaseResponseOutput
	}

	void "searches entities"() {
		given:
		AstronomicalObjectFullRequest astronomicalObjectFullRequest = Mock()
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = Mock()

		when:
		AstronomicalObjectFullResponse astronomicalObjectFullResponseOutput = astronomicalObject.get(astronomicalObjectFullRequest)

		then:
		1 * apiKeySupplierMock.supply(astronomicalObjectFullRequest)
		1 * astronomicalObjectPortTypeMock.getAstronomicalObjectFull(astronomicalObjectFullRequest) >> astronomicalObjectFullResponse
		0 * _
		astronomicalObjectFullResponse == astronomicalObjectFullResponseOutput
	}

}
