package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.species.dto.SpeciesV2RestBeanParams
import com.cezarykluczynski.stapi.server.species.reader.SpeciesV2RestReader

class SpeciesV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private SpeciesV2RestReader speciesV2RestReaderMock

	private SpeciesV2RestEndpoint speciesV2RestEndpoint

	void setup() {
		speciesV2RestReaderMock = Mock()
		speciesV2RestEndpoint = new SpeciesV2RestEndpoint(speciesV2RestReaderMock)
	}

	void "passes get call to SpeciesV2RestReader"() {
		given:
		SpeciesV2FullResponse speciesV2FullResponse = Mock()

		when:
		SpeciesV2FullResponse speciesV2FullResponseOutput = speciesV2RestEndpoint.getSpecies(UID)

		then:
		1 * speciesV2RestReaderMock.readFull(UID) >> speciesV2FullResponse
		speciesV2FullResponseOutput == speciesV2FullResponse
	}

	void "passes search get call to SpeciesV2RestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SpeciesV2BaseResponse speciesV2Response = Mock()

		when:
		SpeciesV2BaseResponse speciesV2ResponseOutput = speciesV2RestEndpoint.searchSpecies(pageAwareBeanParams)

		then:
		1 * speciesV2RestReaderMock.readBase(_ as SpeciesV2RestBeanParams) >> {
			SpeciesV2RestBeanParams speciesV2RestBeanParams ->
				assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
				assert pageAwareBeanParams.pageSize == PAGE_SIZE
				speciesV2Response
		}
		speciesV2ResponseOutput == speciesV2Response
	}

	void "passes search post call to SpeciesV2RestReader"() {
		given:
		SpeciesV2RestBeanParams speciesV2RestBeanParams = new SpeciesV2RestBeanParams(name: NAME)
		SpeciesV2BaseResponse speciesV2Response = Mock()

		when:
		SpeciesV2BaseResponse speciesV2ResponseOutput = speciesV2RestEndpoint.searchSpecies(speciesV2RestBeanParams)

		then:
		1 * speciesV2RestReaderMock.readBase(speciesV2RestBeanParams as SpeciesV2RestBeanParams) >> {
			SpeciesV2RestBeanParams params ->
				assert params.name == NAME
				speciesV2Response
		}
		speciesV2ResponseOutput == speciesV2Response
	}

}
