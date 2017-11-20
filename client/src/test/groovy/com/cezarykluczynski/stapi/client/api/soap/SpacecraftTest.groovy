package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftPortType
import spock.lang.Specification

class SpacecraftTest extends Specification {

	private SpacecraftPortType spacecraftPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Spacecraft spacecraft

	void setup() {
		spacecraftPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		spacecraft = new Spacecraft(spacecraftPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		SpacecraftBaseRequest spacecraftBaseRequest = Mock()
		SpacecraftBaseResponse spacecraftBaseResponse = Mock()

		when:
		SpacecraftBaseResponse spacecraftBaseResponseOutput = spacecraft.search(spacecraftBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(spacecraftBaseRequest)
		1 * spacecraftPortTypeMock.getSpacecraftBase(spacecraftBaseRequest) >> spacecraftBaseResponse
		0 * _
		spacecraftBaseResponse == spacecraftBaseResponseOutput
	}

	void "searches entities"() {
		given:
		SpacecraftFullRequest spacecraftFullRequest = Mock()
		SpacecraftFullResponse spacecraftFullResponse = Mock()

		when:
		SpacecraftFullResponse spacecraftFullResponseOutput = spacecraft.get(spacecraftFullRequest)

		then:
		1 * apiKeySupplierMock.supply(spacecraftFullRequest)
		1 * spacecraftPortTypeMock.getSpacecraftFull(spacecraftFullRequest) >> spacecraftFullResponse
		0 * _
		spacecraftFullResponse == spacecraftFullResponseOutput
	}

}
