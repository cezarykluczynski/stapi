package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams
import com.cezarykluczynski.stapi.server.staff.reader.StaffRestReader

class StaffRestEndpointTest extends AbstractRestEndpointTest {

	private StaffRestReader staffRestReaderMock

	private StaffRestEndpoint staffRestEndpoint

	void setup() {
		staffRestReaderMock = Mock(StaffRestReader)
		staffRestEndpoint = new StaffRestEndpoint(staffRestReaderMock)
	}

	void "passes get call to StaffRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		StaffResponse staffResponse = Mock(StaffResponse)

		when:
		StaffResponse staffResponseOutput = staffRestEndpoint.getStaffs(pageAwareBeanParams)

		then:
		1 * staffRestReaderMock.readBase(_ as StaffRestBeanParams) >> { StaffRestBeanParams staffRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			staffResponse
		}
		staffResponseOutput == staffResponse
	}

	void "passes post call to StaffRestReader"() {
		given:
		StaffRestBeanParams staffRestBeanParams = new StaffRestBeanParams()
		StaffResponse staffResponse = Mock(StaffResponse)

		when:
		StaffResponse staffResponseOutput = staffRestEndpoint.searchStaffs(staffRestBeanParams)

		then:
		1 * staffRestReaderMock.readBase(staffRestBeanParams as StaffRestBeanParams) >> { StaffRestBeanParams params ->
			staffResponse
		}
		staffResponseOutput == staffResponse
	}

}
