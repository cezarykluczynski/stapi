package com.cezarykluczynski.stapi.server.spacecraft.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft.reader.SpacecraftRestReader

class SpacecraftRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private SpacecraftRestReader spacecraftRestReaderMock

	private SpacecraftRestEndpoint spacecraftRestEndpoint

	void setup() {
		spacecraftRestReaderMock = Mock()
		spacecraftRestEndpoint = new SpacecraftRestEndpoint(spacecraftRestReaderMock)
	}

	void "passes get call to SpacecraftRestReader"() {
		given:
		SpacecraftFullResponse spacecraftFullResponse = Mock()

		when:
		SpacecraftFullResponse spacecraftFullResponseOutput = spacecraftRestEndpoint.getSpacecraft(UID)

		then:
		1 * spacecraftRestReaderMock.readFull(UID) >> spacecraftFullResponse
		spacecraftFullResponseOutput == spacecraftFullResponse
	}

	void "passes search get call to SpacecraftRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SpacecraftBaseResponse spacecraftResponse = Mock()

		when:
		SpacecraftBaseResponse spacecraftResponseOutput = spacecraftRestEndpoint.searchSpacecraft(pageAwareBeanParams)

		then:
		1 * spacecraftRestReaderMock.readBase(_ as SpacecraftRestBeanParams) >> { SpacecraftRestBeanParams spacecraftRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			spacecraftResponse
		}
		spacecraftResponseOutput == spacecraftResponse
	}

	void "passes search post call to SpacecraftRestReader"() {
		given:
		SpacecraftRestBeanParams spacecraftRestBeanParams = new SpacecraftRestBeanParams(name: NAME)
		SpacecraftBaseResponse spacecraftResponse = Mock()

		when:
		SpacecraftBaseResponse spacecraftResponseOutput = spacecraftRestEndpoint.searchSpacecraft(spacecraftRestBeanParams)

		then:
		1 * spacecraftRestReaderMock.readBase(spacecraftRestBeanParams as SpacecraftRestBeanParams) >> { SpacecraftRestBeanParams params ->
			assert params.name == NAME
			spacecraftResponse
		}
		spacecraftResponseOutput == spacecraftResponse
	}

}
