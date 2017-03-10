package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesResponse
import com.cezarykluczynski.stapi.server.species.reader.SpeciesSoapReader
import spock.lang.Specification

class SpeciesSoapEndpointTest extends Specification {

	private SpeciesSoapReader speciesSoapReaderMock

	private SpeciesSoapEndpoint speciesSoapEndpoint

	void setup() {
		speciesSoapReaderMock = Mock(SpeciesSoapReader)
		speciesSoapEndpoint = new SpeciesSoapEndpoint(speciesSoapReaderMock)
	}

	void "passes call to SpeciesSoapReader"() {
		given:
		SpeciesRequest speciesRequest = Mock(SpeciesRequest)
		SpeciesResponse speciesResponse = Mock(SpeciesResponse)

		when:
		SpeciesResponse speciesResponseResult = speciesSoapEndpoint.getSpecies(speciesRequest)

		then:
		1 * speciesSoapReaderMock.read(speciesRequest) >> speciesResponse
		speciesResponseResult == speciesResponse
	}

}
