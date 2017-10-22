package com.cezarykluczynski.stapi.server.occupation.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams
import com.cezarykluczynski.stapi.server.occupation.reader.OccupationRestReader

class OccupationRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private OccupationRestReader occupationRestReaderMock

	private OccupationRestEndpoint occupationRestEndpoint

	void setup() {
		occupationRestReaderMock = Mock()
		occupationRestEndpoint = new OccupationRestEndpoint(occupationRestReaderMock)
	}

	void "passes get call to OccupationRestReader"() {
		given:
		OccupationFullResponse occupationFullResponse = Mock()

		when:
		OccupationFullResponse occupationFullResponseOutput = occupationRestEndpoint.getOccupation(UID)

		then:
		1 * occupationRestReaderMock.readFull(UID) >> occupationFullResponse
		occupationFullResponseOutput == occupationFullResponse
	}

	void "passes search get call to OccupationRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		OccupationBaseResponse occupationResponse = Mock()

		when:
		OccupationBaseResponse occupationResponseOutput = occupationRestEndpoint.searchOccupations(pageAwareBeanParams)

		then:
		1 * occupationRestReaderMock.readBase(_ as OccupationRestBeanParams) >> { OccupationRestBeanParams occupationRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			occupationResponse
		}
		occupationResponseOutput == occupationResponse
	}

	void "passes search post call to OccupationRestReader"() {
		given:
		OccupationRestBeanParams occupationRestBeanParams = new OccupationRestBeanParams(name: NAME)
		OccupationBaseResponse occupationResponse = Mock()

		when:
		OccupationBaseResponse occupationResponseOutput = occupationRestEndpoint.searchOccupations(occupationRestBeanParams)

		then:
		1 * occupationRestReaderMock.readBase(occupationRestBeanParams as OccupationRestBeanParams) >> { OccupationRestBeanParams params ->
			assert params.name == NAME
			occupationResponse
		}
		occupationResponseOutput == occupationResponse
	}

}
