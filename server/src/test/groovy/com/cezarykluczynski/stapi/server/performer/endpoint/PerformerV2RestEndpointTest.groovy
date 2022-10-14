package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.performer.dto.PerformerV2RestBeanParams
import com.cezarykluczynski.stapi.server.performer.reader.PerformerV2RestReader

class PerformerV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private PerformerV2RestReader performerV2RestReaderMock

	private PerformerV2RestEndpoint performerV2RestEndpoint

	void setup() {
		performerV2RestReaderMock = Mock()
		performerV2RestEndpoint = new PerformerV2RestEndpoint(performerV2RestReaderMock)
	}

	void "passes get call to PerformerRestReader"() {
		given:
		PerformerV2FullResponse performerV2FullResponse = Mock()

		when:
		PerformerV2FullResponse performerV2FullResponseOutput = performerV2RestEndpoint.getPerformer(UID)

		then:
		1 * performerV2RestReaderMock.readFull(UID) >> performerV2FullResponse
		performerV2FullResponseOutput == performerV2FullResponse
	}

	void "passes search get call to PerformerRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		PerformerV2BaseResponse performerV2Response = Mock()

		when:
		PerformerV2BaseResponse performerV2ResponseOutput = performerV2RestEndpoint.searchPerformer(pageAwareBeanParams)

		then:
		1 * performerV2RestReaderMock.readBase(_ as PerformerV2RestBeanParams) >> { PerformerV2RestBeanParams performerRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			performerV2Response
		}
		performerV2ResponseOutput == performerV2Response
	}

	void "passes search post call to PerformerRestReader"() {
		given:
		PerformerV2RestBeanParams performerV2RestBeanParams = new PerformerV2RestBeanParams(name: NAME)
		PerformerV2BaseResponse performerV2Response = Mock()

		when:
		PerformerV2BaseResponse performerV2ResponseOutput = performerV2RestEndpoint.searchPerformer(performerV2RestBeanParams)

		then:
		1 * performerV2RestReaderMock.readBase(performerV2RestBeanParams as PerformerV2RestBeanParams) >> { PerformerV2RestBeanParams params ->
			assert params.name == NAME
			performerV2Response
		}
		performerV2ResponseOutput == performerV2Response
	}

}
