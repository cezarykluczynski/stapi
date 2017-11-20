package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesPortType
import spock.lang.Specification

class SpeciesTest extends Specification {

	private SpeciesPortType speciesPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Species species

	void setup() {
		speciesPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		species = new Species(speciesPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		SpeciesBaseRequest speciesBaseRequest = Mock()
		SpeciesBaseResponse speciesBaseResponse = Mock()

		when:
		SpeciesBaseResponse speciesBaseResponseOutput = species.search(speciesBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(speciesBaseRequest)
		1 * speciesPortTypeMock.getSpeciesBase(speciesBaseRequest) >> speciesBaseResponse
		0 * _
		speciesBaseResponse == speciesBaseResponseOutput
	}

	void "searches entities"() {
		given:
		SpeciesFullRequest speciesFullRequest = Mock()
		SpeciesFullResponse speciesFullResponse = Mock()

		when:
		SpeciesFullResponse speciesFullResponseOutput = species.get(speciesFullRequest)

		then:
		1 * apiKeySupplierMock.supply(speciesFullRequest)
		1 * speciesPortTypeMock.getSpeciesFull(speciesFullRequest) >> speciesFullResponse
		0 * _
		speciesFullResponse == speciesFullResponseOutput
	}

}
