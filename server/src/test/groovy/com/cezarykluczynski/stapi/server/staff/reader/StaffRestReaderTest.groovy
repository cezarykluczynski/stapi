package com.cezarykluczynski.stapi.server.staff.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBase
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFull
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFullResponse
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams
import com.cezarykluczynski.stapi.server.staff.mapper.StaffRestMapper
import com.cezarykluczynski.stapi.server.staff.query.StaffRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class StaffRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private StaffRestQuery staffRestQueryBuilderMock

	private StaffRestMapper staffRestMapperMock

	private PageMapper pageMapperMock

	private StaffRestReader staffRestReader

	void setup() {
		staffRestQueryBuilderMock = Mock(StaffRestQuery)
		staffRestMapperMock = Mock(StaffRestMapper)
		pageMapperMock = Mock(PageMapper)
		staffRestReader = new StaffRestReader(staffRestQueryBuilderMock, staffRestMapperMock, pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		StaffRestBeanParams staffRestBeanParams = Mock(StaffRestBeanParams)
		List<StaffBase> restStaffList = Lists.newArrayList(Mock(StaffBase))
		List<Staff> staffList = Lists.newArrayList(Mock(Staff))
		Page<Staff> staffPage = Mock(Page)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		StaffBaseResponse staffResponseOutput = staffRestReader.readBase(staffRestBeanParams)

		then:
		1 * staffRestQueryBuilderMock.query(staffRestBeanParams) >> staffPage
		1 * pageMapperMock.fromPageToRestResponsePage(staffPage) >> responsePage
		1 * staffPage.content >> staffList
		1 * staffRestMapperMock.mapBase(staffList) >> restStaffList
		0 * _
		staffResponseOutput.staff == restStaffList
		staffResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		StaffFull staffFull = Mock(StaffFull)
		Staff staff = Mock(Staff)
		List<Staff> staffList = Lists.newArrayList(staff)
		Page<Staff> staffPage = Mock(Page)

		when:
		StaffFullResponse staffResponseOutput = staffRestReader.readFull(GUID)

		then:
		1 * staffRestQueryBuilderMock.query(_ as StaffRestBeanParams) >> { StaffRestBeanParams staffRestBeanParams ->
			assert staffRestBeanParams.guid == GUID
			staffPage
		}
		1 * staffPage.content >> staffList
		1 * staffRestMapperMock.mapFull(staff) >> staffFull
		0 * _
		staffResponseOutput.staff == staffFull
	}

}
