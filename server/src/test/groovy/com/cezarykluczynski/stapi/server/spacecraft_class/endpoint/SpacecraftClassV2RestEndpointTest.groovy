package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassV2RestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft_class.reader.SpacecraftClassV2RestReader

class SpacecraftClassV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private SpacecraftClassV2RestReader spacecraftClassV2RestReaderMock

	private SpacecraftClassV2RestEndpoint spacecraftClassV2RestEndpoint

	void setup() {
		spacecraftClassV2RestReaderMock = Mock()
		spacecraftClassV2RestEndpoint = new SpacecraftClassV2RestEndpoint(spacecraftClassV2RestReaderMock)
	}

	void "passes get call to SpacecraftClassV2RestReader"() {
		given:
		SpacecraftClassV2FullResponse spacecraftClassV2FullResponse = Mock()

		when:
		SpacecraftClassV2FullResponse spacecraftClassV2FullResponseOutput = spacecraftClassV2RestEndpoint.getSpacecraftClass(UID)

		then:
		1 * spacecraftClassV2RestReaderMock.readFull(UID) >> spacecraftClassV2FullResponse
		spacecraftClassV2FullResponseOutput == spacecraftClassV2FullResponse
	}

	void "passes search get call to SpacecraftClassV2RestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SpacecraftClassV2BaseResponse spacecraftClassV2Response = Mock()

		when:
		SpacecraftClassV2BaseResponse spacecraftClassV2ResponseOutput = spacecraftClassV2RestEndpoint.searchSpacecraftClass(pageAwareBeanParams)

		then:
		1 * spacecraftClassV2RestReaderMock.readBase(_ as SpacecraftClassV2RestBeanParams) >> {
			SpacecraftClassV2RestBeanParams spacecraftClassV2RestBeanParams ->
				assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
				assert pageAwareBeanParams.pageSize == PAGE_SIZE
				spacecraftClassV2Response
		}
		spacecraftClassV2ResponseOutput == spacecraftClassV2Response
	}

	void "passes search post call to SpacecraftClassV2RestReader"() {
		given:
		SpacecraftClassV2RestBeanParams spacecraftClassV2RestBeanParams = new SpacecraftClassV2RestBeanParams(name: NAME)
		SpacecraftClassV2BaseResponse spacecraftClassV2Response = Mock()

		when:
		SpacecraftClassV2BaseResponse spacecraftClassV2ResponseOutput = spacecraftClassV2RestEndpoint.searchSpacecraftClass(spacecraftClassV2RestBeanParams)

		then:
		1 * spacecraftClassV2RestReaderMock.readBase(spacecraftClassV2RestBeanParams as SpacecraftClassV2RestBeanParams) >> {
			SpacecraftClassV2RestBeanParams params ->
				assert params.name == NAME
				spacecraftClassV2Response
		}
		spacecraftClassV2ResponseOutput == spacecraftClassV2Response
	}

}
