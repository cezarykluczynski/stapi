package com.cezarykluczynski.stapi.server.spacecraft.endpoint

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftV2RestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft.reader.SpacecraftV2RestReader

class SpacecraftV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private SpacecraftV2RestReader spacecraftV2RestReaderMock

	private SpacecraftV2RestEndpoint spacecraftV2RestEndpoint

	void setup() {
		spacecraftV2RestReaderMock = Mock()
		spacecraftV2RestEndpoint = new SpacecraftV2RestEndpoint(spacecraftV2RestReaderMock)
	}

	void "passes get call to SpacecraftV2RestReader"() {
		given:
		SpacecraftV2FullResponse spacecraftV2FullResponse = Mock()

		when:
		SpacecraftV2FullResponse spacecraftV2FullResponseOutput = spacecraftV2RestEndpoint.getSpacecraft(UID)

		then:
		1 * spacecraftV2RestReaderMock.readFull(UID) >> spacecraftV2FullResponse
		spacecraftV2FullResponseOutput == spacecraftV2FullResponse
	}

	void "passes search get call to SpacecraftV2RestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SpacecraftV2BaseResponse spacecraftV2Response = Mock()

		when:
		SpacecraftV2BaseResponse spacecraftV2ResponseOutput = spacecraftV2RestEndpoint.searchSpacecraft(pageAwareBeanParams)

		then:
		1 * spacecraftV2RestReaderMock.readBase(_ as SpacecraftV2RestBeanParams) >> {
			SpacecraftV2RestBeanParams spacecraftV2RestBeanParams ->
				assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
				assert pageAwareBeanParams.pageSize == PAGE_SIZE
				spacecraftV2Response
		}
		spacecraftV2ResponseOutput == spacecraftV2Response
	}

	void "passes search post call to SpacecraftV2RestReader"() {
		given:
		SpacecraftV2RestBeanParams spacecraftV2RestBeanParams = new SpacecraftV2RestBeanParams(name: NAME)
		SpacecraftV2BaseResponse spacecraftV2Response = Mock()

		when:
		SpacecraftV2BaseResponse spacecraftV2ResponseOutput = spacecraftV2RestEndpoint.searchSpacecraft(spacecraftV2RestBeanParams)

		then:
		1 * spacecraftV2RestReaderMock.readBase(spacecraftV2RestBeanParams as SpacecraftV2RestBeanParams) >> {
			SpacecraftV2RestBeanParams params ->
				assert params.name == NAME
				spacecraftV2Response
		}
		spacecraftV2ResponseOutput == spacecraftV2Response
	}

}
