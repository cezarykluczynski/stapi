package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft_class.reader.SpacecraftClassRestReader

class SpacecraftClassRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private SpacecraftClassRestReader spacecraftClassRestReaderMock

	private SpacecraftClassRestEndpoint spacecraftClassRestEndpoint

	void setup() {
		spacecraftClassRestReaderMock = Mock()
		spacecraftClassRestEndpoint = new SpacecraftClassRestEndpoint(spacecraftClassRestReaderMock)
	}

	void "passes get call to SpacecraftClassRestReader"() {
		given:
		SpacecraftClassFullResponse spacecraftClassFullResponse = Mock()

		when:
		SpacecraftClassFullResponse spacecraftClassFullResponseOutput = spacecraftClassRestEndpoint.getSpacecraftClass(UID)

		then:
		1 * spacecraftClassRestReaderMock.readFull(UID) >> spacecraftClassFullResponse
		spacecraftClassFullResponseOutput == spacecraftClassFullResponse
	}

	void "passes search get call to SpacecraftClassRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SpacecraftClassBaseResponse spacecraftClassResponse = Mock()

		when:
		SpacecraftClassBaseResponse spacecraftClassResponseOutput = spacecraftClassRestEndpoint.searchSpacecraftClass(pageAwareBeanParams)

		then:
		1 * spacecraftClassRestReaderMock.readBase(_ as SpacecraftClassRestBeanParams) >> {
				SpacecraftClassRestBeanParams spacecraftClassRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			spacecraftClassResponse
		}
		spacecraftClassResponseOutput == spacecraftClassResponse
	}

	void "passes search post call to SpacecraftClassRestReader"() {
		given:
		SpacecraftClassRestBeanParams spacecraftClassRestBeanParams = new SpacecraftClassRestBeanParams(name: NAME)
		SpacecraftClassBaseResponse spacecraftClassResponse = Mock()

		when:
		SpacecraftClassBaseResponse spacecraftClassResponseOutput = spacecraftClassRestEndpoint.searchSpacecraftClass(spacecraftClassRestBeanParams)

		then:
		1 * spacecraftClassRestReaderMock.readBase(spacecraftClassRestBeanParams as SpacecraftClassRestBeanParams) >> {
				SpacecraftClassRestBeanParams params ->
			assert params.name == NAME
			spacecraftClassResponse
		}
		spacecraftClassResponseOutput == spacecraftClassResponse
	}

}
