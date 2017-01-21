package com.cezarykluczynski.stapi.server.staff.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.Staff as SOAPStaff
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffResponse
import com.cezarykluczynski.stapi.model.staff.entity.Staff as DBStaff
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

	void "gets database entities and puts them into StaffResponse"() {
		given:
		List<DBStaff> dbStaffList = Lists.newArrayList()
		Page<DBStaff> dbStaffPage = Mock(Page)
		dbStaffPage.content >> dbStaffList
		List<SOAPStaff> soapStaffList = Lists.newArrayList(new SOAPStaff(guid: GUID))
		StaffRestBeanParams seriesRestBeanParams = Mock(StaffRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		StaffResponse staffResponse = staffRestReader.read(seriesRestBeanParams)

		then:
		1 * staffRestQueryBuilderMock.query(seriesRestBeanParams) >> dbStaffPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbStaffPage) >> responsePage
		1 * staffRestMapperMock.map(dbStaffList) >> soapStaffList
		staffResponse.staff[0].guid == GUID
		staffResponse.page == responsePage
	}

}
