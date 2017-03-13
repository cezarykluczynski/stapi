package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.cezarykluczynski.stapi.server.performer.reader.PerformerRestReader

class PerformerRestEndpointTest extends AbstractRestEndpointTest {

	private PerformerRestReader performerRestReaderMock

	private PerformerRestEndpoint performerRestEndpoint

	void setup() {
		performerRestReaderMock = Mock(PerformerRestReader)
		performerRestEndpoint = new PerformerRestEndpoint(performerRestReaderMock)
	}

	void "passes get call to PerformerRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		PerformerResponse performerResponse = Mock(PerformerResponse)

		when:
		PerformerResponse performerResponseOutput = performerRestEndpoint.getPerformers(pageAwareBeanParams)

		then:
		1 * performerRestReaderMock.readBase(_ as PerformerRestBeanParams) >> { PerformerRestBeanParams performerRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			performerResponse
		}
		performerResponseOutput == performerResponse
	}

	void "passes post call to PerformerRestReader"() {
		given:
		PerformerRestBeanParams performerRestBeanParams = new PerformerRestBeanParams()
		PerformerResponse performerResponse = Mock(PerformerResponse)

		when:
		PerformerResponse performerResponseOutput = performerRestEndpoint.searchPerformers(performerRestBeanParams)

		then:
		1 * performerRestReaderMock.readBase(performerRestBeanParams as PerformerRestBeanParams) >> { PerformerRestBeanParams params ->
			performerResponse
		}
		performerResponseOutput == performerResponse
	}

}
