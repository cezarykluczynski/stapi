package com.cezarykluczynski.stapi.server.conflict.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams
import com.cezarykluczynski.stapi.server.conflict.reader.ConflictRestReader

class ConflictRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private ConflictRestReader conflictRestReaderMock

	private ConflictRestEndpoint conflictRestEndpoint

	void setup() {
		conflictRestReaderMock = Mock()
		conflictRestEndpoint = new ConflictRestEndpoint(conflictRestReaderMock)
	}

	void "passes get call to ConflictRestReader"() {
		given:
		ConflictFullResponse conflictFullResponse = Mock()

		when:
		ConflictFullResponse conflictFullResponseOutput = conflictRestEndpoint.getConflict(UID)

		then:
		1 * conflictRestReaderMock.readFull(UID) >> conflictFullResponse
		conflictFullResponseOutput == conflictFullResponse
	}

	void "passes search get call to ConflictRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ConflictBaseResponse conflictResponse = Mock()

		when:
		ConflictBaseResponse conflictResponseOutput = conflictRestEndpoint.searchConflict(pageAwareBeanParams)

		then:
		1 * conflictRestReaderMock.readBase(_ as ConflictRestBeanParams) >> { ConflictRestBeanParams conflictRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			conflictResponse
		}
		conflictResponseOutput == conflictResponse
	}

	void "passes search post call to ConflictRestReader"() {
		given:
		ConflictRestBeanParams conflictRestBeanParams = new ConflictRestBeanParams(name: NAME)
		ConflictBaseResponse conflictResponse = Mock()

		when:
		ConflictBaseResponse conflictResponseOutput = conflictRestEndpoint.searchConflict(conflictRestBeanParams)

		then:
		1 * conflictRestReaderMock.readBase(conflictRestBeanParams as ConflictRestBeanParams) >> { ConflictRestBeanParams params ->
			assert params.name == NAME
			conflictResponse
		}
		conflictResponseOutput == conflictResponse
	}

}
