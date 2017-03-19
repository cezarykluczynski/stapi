package com.cezarykluczynski.stapi.server.staff.reader

import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.StaffBase
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.StaffFull
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullResponse
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullSoapMapper
import com.cezarykluczynski.stapi.server.staff.query.StaffSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class StaffSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private StaffSoapQuery staffSoapQueryBuilderMock

	private StaffBaseSoapMapper staffBaseSoapMapperMock

	private StaffFullSoapMapper staffFullSoapMapperMock

	private PageMapper pageMapperMock

	private StaffSoapReader staffSoapReader

	void setup() {
		staffSoapQueryBuilderMock = Mock(StaffSoapQuery)
		staffBaseSoapMapperMock = Mock(StaffBaseSoapMapper)
		staffFullSoapMapperMock = Mock(StaffFullSoapMapper)
		pageMapperMock = Mock(PageMapper)
		staffSoapReader = new StaffSoapReader(staffSoapQueryBuilderMock, staffBaseSoapMapperMock, staffFullSoapMapperMock, pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Staff> dbStaffList = Lists.newArrayList()
		Page<Staff> dbStaffPage = Mock(Page)
		List<StaffBase> soapStaffList = Lists.newArrayList(new StaffBase(guid: GUID))
		StaffBaseRequest staffBaseRequest = Mock(StaffBaseRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		StaffBaseResponse staffResponse = staffSoapReader.readBase(staffBaseRequest)

		then:
		1 * staffSoapQueryBuilderMock.query(staffBaseRequest) >> dbStaffPage
		1 * dbStaffPage.content >> dbStaffList
		1 * pageMapperMock.fromPageToSoapResponsePage(dbStaffPage) >> responsePage
		1 * staffBaseSoapMapperMock.mapBase(dbStaffList) >> soapStaffList
		staffResponse.staff[0].guid == GUID
		staffResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		StaffFull staffFull = new StaffFull(guid: GUID)
		Staff staff = Mock(Staff)
		Page<Staff> staffPage = Mock(Page)
		StaffFullRequest staffFullRequest = Mock(StaffFullRequest)

		when:
		StaffFullResponse staffFullResponse = staffSoapReader.readFull(staffFullRequest)

		then:
		1 * staffSoapQueryBuilderMock.query(staffFullRequest) >> staffPage
		1 * staffPage.content >> Lists.newArrayList(staff)
		1 * staffFullSoapMapperMock.mapFull(staff) >> staffFull
		staffFullResponse.staff.guid == GUID
	}

}
