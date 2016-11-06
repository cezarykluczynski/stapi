package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerResponse
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.cezarykluczynski.stapi.server.performer.reader.PerformerRestReader

class PerformerRestEndpointTest extends AbstractRestEndpointTest {

	private PerformerRestReader performerRestReaderMock

	private PerformerRestEndpoint performerRestEndpoint

	def setup() {
		performerRestReaderMock = Mock(PerformerRestReader)
		performerRestEndpoint = new PerformerRestEndpoint(performerRestReaderMock)
	}

	def "passes get call to PerformerRestReader"() {
		given:
		PageAwareBeanParams pageAwareBeanParams = Mock(PageAwareBeanParams) {
			getPageNumber() >> PAGE_NUMBER
			getPageSize() >> PAGE_SIZE
		}
		PerformerResponse performerResponse = Mock(PerformerResponse)

		when:
		PerformerResponse performerResponseOutput = performerRestEndpoint.getPerformers(pageAwareBeanParams)

		then:
		1 * performerRestReaderMock.read(_ as PerformerRestBeanParams) >> { PerformerRestBeanParams performerRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			return performerResponse
		}
		performerResponseOutput == performerResponse
	}

	def "passes post call to PerformerRestReader"() {
		given:
		PerformerRestBeanParams performerRestBeanParams = new PerformerRestBeanParams()
		PerformerResponse performerResponse = Mock(PerformerResponse)

		when:
		PerformerResponse performerResponseOutput = performerRestEndpoint.searchPerformers(performerRestBeanParams)

		then:
		1 * performerRestReaderMock.read(performerRestBeanParams as PerformerRestBeanParams) >> { PerformerRestBeanParams params ->
			return performerResponse
		}
		performerResponseOutput == performerResponse
	}

}
