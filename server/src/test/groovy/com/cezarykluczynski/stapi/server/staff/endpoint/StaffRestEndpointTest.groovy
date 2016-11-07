package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffResponse
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams
import com.cezarykluczynski.stapi.server.staff.reader.StaffRestReader

class StaffRestEndpointTest extends AbstractRestEndpointTest {

	private StaffRestReader staffRestReaderMock

	private StaffRestEndpoint staffRestEndpoint

	def setup() {
		staffRestReaderMock = Mock(StaffRestReader)
		staffRestEndpoint = new StaffRestEndpoint(staffRestReaderMock)
	}

	def "passes get call to StaffRestReader"() {
		given:
		PageAwareBeanParams pageAwareBeanParams = Mock(PageAwareBeanParams) {
			getPageNumber() >> PAGE_NUMBER
			getPageSize() >> PAGE_SIZE
		}
		StaffResponse staffResponse = Mock(StaffResponse)

		when:
		StaffResponse staffResponseOutput = staffRestEndpoint.getStaffs(pageAwareBeanParams)

		then:
		1 * staffRestReaderMock.read(_ as StaffRestBeanParams) >> { StaffRestBeanParams staffRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			return staffResponse
		}
		staffResponseOutput == staffResponse
	}

	def "passes post call to StaffRestReader"() {
		given:
		StaffRestBeanParams staffRestBeanParams = new StaffRestBeanParams()
		StaffResponse staffResponse = Mock(StaffResponse)

		when:
		StaffResponse staffResponseOutput = staffRestEndpoint.searchStaffs(staffRestBeanParams)

		then:
		1 * staffRestReaderMock.read(staffRestBeanParams as StaffRestBeanParams) >> { StaffRestBeanParams params ->
			return staffResponse
		}
		staffResponseOutput == staffResponse
	}

}
