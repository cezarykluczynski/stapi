package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams
import com.cezarykluczynski.stapi.server.staff.reader.StaffRestReader

class StaffRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private StaffRestReader staffRestReaderMock

	private StaffRestEndpoint staffRestEndpoint

	void setup() {
		staffRestReaderMock = Mock()
		staffRestEndpoint = new StaffRestEndpoint(staffRestReaderMock)
	}

	void "passes get call to StaffRestReader"() {
		given:
		StaffFullResponse staffFullResponse = Mock()

		when:
		StaffFullResponse staffFullResponseOutput = staffRestEndpoint.getStaff(UID)

		then:
		1 * staffRestReaderMock.readFull(UID) >> staffFullResponse
		staffFullResponseOutput == staffFullResponse
	}

	void "passes search get call to StaffRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		StaffBaseResponse staffResponse = Mock()

		when:
		StaffBaseResponse staffResponseOutput = staffRestEndpoint.searchStaff(pageAwareBeanParams)

		then:
		1 * staffRestReaderMock.readBase(_ as StaffRestBeanParams) >> { StaffRestBeanParams staffRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			staffResponse
		}
		staffResponseOutput == staffResponse
	}

	void "passes search post call to StaffRestReader"() {
		given:
		StaffRestBeanParams staffRestBeanParams = new StaffRestBeanParams(name: NAME)
		StaffBaseResponse staffResponse = Mock()

		when:
		StaffBaseResponse staffResponseOutput = staffRestEndpoint.searchStaff(staffRestBeanParams)

		then:
		1 * staffRestReaderMock.readBase(staffRestBeanParams as StaffRestBeanParams) >> { StaffRestBeanParams params ->
			assert params.name == NAME
			staffResponse
		}
		staffResponseOutput == staffResponse
	}

}
