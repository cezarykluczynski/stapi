package com.cezarykluczynski.stapi.server.conflict.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams
import com.cezarykluczynski.stapi.server.conflict.reader.ConflictV2RestReader

class ConflictV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private ConflictV2RestReader conflictV2RestReaderMock

	private ConflictV2RestEndpoint conflictV2RestEndpoint

	void setup() {
		conflictV2RestReaderMock = Mock()
		conflictV2RestEndpoint = new ConflictV2RestEndpoint(conflictV2RestReaderMock)
	}

	void "passes get call to ConflictRestReader"() {
		given:
		ConflictV2FullResponse conflictV2FullResponse = Mock()

		when:
		ConflictV2FullResponse conflictV2FullResponseOutput = conflictV2RestEndpoint.getConflict(UID)

		then:
		1 * conflictV2RestReaderMock.readFull(UID) >> conflictV2FullResponse
		conflictV2FullResponseOutput == conflictV2FullResponse
	}

	void "passes search get call to ConflictRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ConflictV2BaseResponse conflictV2Response = Mock()

		when:
		ConflictV2BaseResponse conflictV2ResponseOutput = conflictV2RestEndpoint
				.searchConflict(pageAwareBeanParams)

		then:
		1 * conflictV2RestReaderMock.readBase(_ as ConflictRestBeanParams) >> { ConflictRestBeanParams conflictRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			conflictV2Response
		}
		conflictV2ResponseOutput == conflictV2Response
	}

	void "passes search post call to ConflictRestReader"() {
		given:
		ConflictRestBeanParams conflictRestBeanParams = new ConflictRestBeanParams(name: NAME)
		ConflictV2BaseResponse conflictV2Response = Mock()

		when:
		ConflictV2BaseResponse conflictV2ResponseOutput = conflictV2RestEndpoint.searchConflict(conflictRestBeanParams)

		then:
		1 * conflictV2RestReaderMock.readBase(conflictRestBeanParams as ConflictRestBeanParams) >> {
			ConflictRestBeanParams params ->
				assert params.name == NAME
				conflictV2Response
		}
		conflictV2ResponseOutput == conflictV2Response
	}

}
