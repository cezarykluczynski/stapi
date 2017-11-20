package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.AnimalPortType
import spock.lang.Specification

class AnimalTest extends Specification {

	private AnimalPortType animalPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Animal animal

	void setup() {
		animalPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		animal = new Animal(animalPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		AnimalBaseRequest animalBaseRequest = Mock()
		AnimalBaseResponse animalBaseResponse = Mock()

		when:
		AnimalBaseResponse animalBaseResponseOutput = animal.search(animalBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(animalBaseRequest)
		1 * animalPortTypeMock.getAnimalBase(animalBaseRequest) >> animalBaseResponse
		0 * _
		animalBaseResponse == animalBaseResponseOutput
	}

	void "searches entities"() {
		given:
		AnimalFullRequest animalFullRequest = Mock()
		AnimalFullResponse animalFullResponse = Mock()

		when:
		AnimalFullResponse animalFullResponseOutput = animal.get(animalFullRequest)

		then:
		1 * apiKeySupplierMock.supply(animalFullRequest)
		1 * animalPortTypeMock.getAnimalFull(animalFullRequest) >> animalFullResponse
		0 * _
		animalFullResponse == animalFullResponseOutput
	}

}
