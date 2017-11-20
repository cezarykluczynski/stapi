package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassPortType
import spock.lang.Specification

class SpacecraftClassTest extends Specification {

	private SpacecraftClassPortType spacecraftClassPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private SpacecraftClass spacecraftClass

	void setup() {
		spacecraftClassPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		spacecraftClass = new SpacecraftClass(spacecraftClassPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		SpacecraftClassBaseRequest spacecraftClassBaseRequest = Mock()
		SpacecraftClassBaseResponse spacecraftClassBaseResponse = Mock()

		when:
		SpacecraftClassBaseResponse spacecraftClassBaseResponseOutput = spacecraftClass.search(spacecraftClassBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(spacecraftClassBaseRequest)
		1 * spacecraftClassPortTypeMock.getSpacecraftClassBase(spacecraftClassBaseRequest) >> spacecraftClassBaseResponse
		0 * _
		spacecraftClassBaseResponse == spacecraftClassBaseResponseOutput
	}

	void "searches entities"() {
		given:
		SpacecraftClassFullRequest spacecraftClassFullRequest = Mock()
		SpacecraftClassFullResponse spacecraftClassFullResponse = Mock()

		when:
		SpacecraftClassFullResponse spacecraftClassFullResponseOutput = spacecraftClass.get(spacecraftClassFullRequest)

		then:
		1 * apiKeySupplierMock.supply(spacecraftClassFullRequest)
		1 * spacecraftClassPortTypeMock.getSpacecraftClassFull(spacecraftClassFullRequest) >> spacecraftClassFullResponse
		0 * _
		spacecraftClassFullResponse == spacecraftClassFullResponseOutput
	}

}
