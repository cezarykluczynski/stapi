package com.cezarykluczynski.stapi.server.staff.reader

import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.Staff as SOAPStaff
import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffResponse
import com.cezarykluczynski.stapi.model.staff.entity.Staff as DBStaff
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffSoapMapper
import com.cezarykluczynski.stapi.server.staff.query.StaffSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class StaffSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private StaffSoapQuery staffSoapQueryBuilderMock

	private StaffSoapMapper staffSoapMapperMock

	private PageMapper pageMapperMock

	private StaffSoapReader staffSoapReader

	void setup() {
		staffSoapQueryBuilderMock = Mock(StaffSoapQuery)
		staffSoapMapperMock = Mock(StaffSoapMapper)
		pageMapperMock = Mock(PageMapper)
		staffSoapReader = new StaffSoapReader(staffSoapQueryBuilderMock, staffSoapMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into StaffResponse"() {
		given:
		List<DBStaff> dbStaffList = Lists.newArrayList()
		Page<DBStaff> dbStaffPage = Mock(Page)
		dbStaffPage.content >> dbStaffList
		List<SOAPStaff> soapStaffList = Lists.newArrayList(new SOAPStaff(guid: GUID))
		StaffRequest staffRequest = Mock(StaffRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		StaffResponse staffResponse = staffSoapReader.readBase(staffRequest)

		then:
		1 * staffSoapQueryBuilderMock.query(staffRequest) >> dbStaffPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbStaffPage) >> responsePage
		1 * staffSoapMapperMock.map(dbStaffList) >> soapStaffList
		staffResponse.staff[0].guid == GUID
		staffResponse.page == responsePage
	}

}
