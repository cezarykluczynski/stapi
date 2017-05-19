package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.cezarykluczynski.stapi.server.performer.reader.PerformerRestReader

class PerformerRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private PerformerRestReader performerRestReaderMock

	private PerformerRestEndpoint performerRestEndpoint

	void setup() {
		performerRestReaderMock = Mock()
		performerRestEndpoint = new PerformerRestEndpoint(performerRestReaderMock)
	}

	void "passes get call to PerformerRestReader"() {
		given:
		PerformerFullResponse performerFullResponse = Mock()

		when:
		PerformerFullResponse performerFullResponseOutput = performerRestEndpoint.getPerformer(UID)

		then:
		1 * performerRestReaderMock.readFull(UID) >> performerFullResponse
		performerFullResponseOutput == performerFullResponse
	}

	void "passes search get call to PerformerRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		PerformerBaseResponse performerResponse = Mock()

		when:
		PerformerBaseResponse performerResponseOutput = performerRestEndpoint.searchPerformer(pageAwareBeanParams)

		then:
		1 * performerRestReaderMock.readBase(_ as PerformerRestBeanParams) >> { PerformerRestBeanParams performerRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			performerResponse
		}
		performerResponseOutput == performerResponse
	}

	void "passes search post call to PerformerRestReader"() {
		given:
		PerformerRestBeanParams performerRestBeanParams = new PerformerRestBeanParams(name: NAME)
		PerformerBaseResponse performerResponse = Mock()

		when:
		PerformerBaseResponse performerResponseOutput = performerRestEndpoint.searchPerformer(performerRestBeanParams)

		then:
		1 * performerRestReaderMock.readBase(performerRestBeanParams as PerformerRestBeanParams) >> { PerformerRestBeanParams params ->
			assert params.name == NAME
			performerResponse
		}
		performerResponseOutput == performerResponse
	}

}
