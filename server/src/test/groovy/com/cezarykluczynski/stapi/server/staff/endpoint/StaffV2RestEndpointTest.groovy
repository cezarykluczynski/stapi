package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.rest.model.StaffV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.StaffV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.staff.dto.StaffV2RestBeanParams
import com.cezarykluczynski.stapi.server.staff.reader.StaffV2RestReader

class StaffV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private StaffV2RestReader staffV2RestReaderMock

	private StaffV2RestEndpoint staffV2RestEndpoint

	void setup() {
		staffV2RestReaderMock = Mock()
		staffV2RestEndpoint = new StaffV2RestEndpoint(staffV2RestReaderMock)
	}

	void "passes get call to StaffRestReader"() {
		given:
		StaffV2FullResponse staffV2FullResponse = Mock()

		when:
		StaffV2FullResponse staffV2FullResponseOutput = staffV2RestEndpoint.getStaff(UID)

		then:
		1 * staffV2RestReaderMock.readFull(UID) >> staffV2FullResponse
		staffV2FullResponseOutput == staffV2FullResponse
	}

	void "passes search get call to StaffRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		StaffV2BaseResponse staffV2Response = Mock()

		when:
		StaffV2BaseResponse staffV2ResponseOutput = staffV2RestEndpoint.searchStaff(pageAwareBeanParams)

		then:
		1 * staffV2RestReaderMock.readBase(_ as StaffV2RestBeanParams) >> { StaffV2RestBeanParams staffRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			staffV2Response
		}
		staffV2ResponseOutput == staffV2Response
	}

	void "passes search post call to StaffRestReader"() {
		given:
		StaffV2RestBeanParams staffV2RestBeanParams = new StaffV2RestBeanParams(name: NAME)
		StaffV2BaseResponse staffV2Response = Mock()

		when:
		StaffV2BaseResponse staffV2ResponseOutput = staffV2RestEndpoint.searchStaff(staffV2RestBeanParams)

		then:
		1 * staffV2RestReaderMock.readBase(staffV2RestBeanParams as StaffV2RestBeanParams) >> { StaffV2RestBeanParams params ->
			assert params.name == NAME
			staffV2Response
		}
		staffV2ResponseOutput == staffV2Response
	}

}
