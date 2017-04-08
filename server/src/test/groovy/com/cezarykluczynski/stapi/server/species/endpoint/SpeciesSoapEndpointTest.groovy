package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullResponse
import com.cezarykluczynski.stapi.server.species.reader.SpeciesSoapReader
import spock.lang.Specification

class SpeciesSoapEndpointTest extends Specification {

	private SpeciesSoapReader speciesSoapReaderMock

	private SpeciesSoapEndpoint speciesSoapEndpoint

	void setup() {
		speciesSoapReaderMock = Mock()
		speciesSoapEndpoint = new SpeciesSoapEndpoint(speciesSoapReaderMock)
	}

	void "passes base call to SpeciesSoapReader"() {
		given:
		SpeciesBaseRequest speciesBaseRequest = Mock()
		SpeciesBaseResponse speciesBaseResponse = Mock()

		when:
		SpeciesBaseResponse speciesResponseResult = speciesSoapEndpoint.getSpeciesBase(speciesBaseRequest)

		then:
		1 * speciesSoapReaderMock.readBase(speciesBaseRequest) >> speciesBaseResponse
		speciesResponseResult == speciesBaseResponse
	}

	void "passes full call to SpeciesSoapReader"() {
		given:
		SpeciesFullRequest speciesFullRequest = Mock()
		SpeciesFullResponse speciesFullResponse = Mock()

		when:
		SpeciesFullResponse speciesResponseResult = speciesSoapEndpoint.getSpeciesFull(speciesFullRequest)

		then:
		1 * speciesSoapReaderMock.readFull(speciesFullRequest) >> speciesFullResponse
		speciesResponseResult == speciesFullResponse
	}

}
