package com.cezarykluczynski.stapi.server.animal.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullResponse
import com.cezarykluczynski.stapi.server.animal.reader.AnimalSoapReader
import spock.lang.Specification

class AnimalSoapEndpointTest extends Specification {

	private AnimalSoapReader animalSoapReaderMock

	private AnimalSoapEndpoint animalSoapEndpoint

	void setup() {
		animalSoapReaderMock = Mock()
		animalSoapEndpoint = new AnimalSoapEndpoint(animalSoapReaderMock)
	}

	void "passes base call to AnimalSoapReader"() {
		given:
		AnimalBaseRequest animalRequest = Mock()
		AnimalBaseResponse animalResponse = Mock()

		when:
		AnimalBaseResponse animalResponseResult = animalSoapEndpoint.getAnimalBase(animalRequest)

		then:
		1 * animalSoapReaderMock.readBase(animalRequest) >> animalResponse
		animalResponseResult == animalResponse
	}

	void "passes full call to AnimalSoapReader"() {
		given:
		AnimalFullRequest animalFullRequest = Mock()
		AnimalFullResponse animalFullResponse = Mock()

		when:
		AnimalFullResponse animalResponseResult = animalSoapEndpoint.getAnimalFull(animalFullRequest)

		then:
		1 * animalSoapReaderMock.readFull(animalFullRequest) >> animalFullResponse
		animalResponseResult == animalFullResponse
	}

}
