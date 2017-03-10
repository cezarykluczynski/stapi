package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesResponse
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams
import com.cezarykluczynski.stapi.server.species.reader.SpeciesRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class SpeciesRestEndpointTest extends AbstractRestEndpointTest {

	private SpeciesRestReader speciesRestReaderMock

	private SpeciesRestEndpoint speciesRestEndpoint

	void setup() {
		speciesRestReaderMock = Mock(SpeciesRestReader)
		speciesRestEndpoint = new SpeciesRestEndpoint(speciesRestReaderMock)
	}

	void "passes get call to SpeciesRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SpeciesResponse speciesResponse = Mock(SpeciesResponse)

		when:
		SpeciesResponse speciesResponseOutput = speciesRestEndpoint.getSpecies(pageAwareBeanParams)

		then:
		1 * speciesRestReaderMock.read(_ as SpeciesRestBeanParams) >> { SpeciesRestBeanParams speciesRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			speciesResponse
		}
		speciesResponseOutput == speciesResponse
	}

	void "passes post call to SpeciesRestReader"() {
		given:
		SpeciesRestBeanParams speciesRestBeanParams = new SpeciesRestBeanParams()
		SpeciesResponse speciesResponse = Mock(SpeciesResponse)

		when:
		SpeciesResponse speciesResponseOutput = speciesRestEndpoint.searchSpecies(speciesRestBeanParams)

		then:
		1 * speciesRestReaderMock.read(speciesRestBeanParams as SpeciesRestBeanParams) >> { SpeciesRestBeanParams params ->
			speciesResponse
		}
		speciesResponseOutput == speciesResponse
	}

}
