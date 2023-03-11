package com.cezarykluczynski.stapi.server.occupation.endpoint

import com.cezarykluczynski.stapi.client.rest.model.OccupationV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationV2RestBeanParams
import com.cezarykluczynski.stapi.server.occupation.reader.OccupationV2RestReader

class OccupationV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private OccupationV2RestReader occupationV2RestReaderMock

	private OccupationV2RestEndpoint occupationV2RestEndpoint

	void setup() {
		occupationV2RestReaderMock = Mock()
		occupationV2RestEndpoint = new OccupationV2RestEndpoint(occupationV2RestReaderMock)
	}

	void "passes get call to OccupationRestReader"() {
		given:
		OccupationV2FullResponse occupationV2FullResponse = Mock()

		when:
		OccupationV2FullResponse occupationV2FullResponseOutput = occupationV2RestEndpoint.getOccupation(UID)

		then:
		1 * occupationV2RestReaderMock.readFull(UID) >> occupationV2FullResponse
		occupationV2FullResponseOutput == occupationV2FullResponse
	}

	void "passes search get call to OccupationRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		OccupationV2BaseResponse occupationV2Response = Mock()

		when:
		OccupationV2BaseResponse occupationV2ResponseOutput = occupationV2RestEndpoint
				.searchOccupation(pageAwareBeanParams)

		then:
		1 * occupationV2RestReaderMock.readBase(_ as OccupationV2RestBeanParams) >> { OccupationV2RestBeanParams occupationRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			occupationV2Response
		}
		occupationV2ResponseOutput == occupationV2Response
	}

	void "passes search post call to OccupationRestReader"() {
		given:
		OccupationV2RestBeanParams occupationV2RestBeanParams = new OccupationV2RestBeanParams(name: NAME)
		OccupationV2BaseResponse occupationV2Response = Mock()

		when:
		OccupationV2BaseResponse occupationV2ResponseOutput = occupationV2RestEndpoint.searchOccupation(occupationV2RestBeanParams)

		then:
		1 * occupationV2RestReaderMock.readBase(occupationV2RestBeanParams as OccupationV2RestBeanParams) >> {
			OccupationV2RestBeanParams params ->
				assert params.name == NAME
				occupationV2Response
		}
		occupationV2ResponseOutput == occupationV2Response
	}

}
