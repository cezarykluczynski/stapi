package com.cezarykluczynski.stapi.server.staff.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.StaffBase
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.StaffFull
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullResponse
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullSoapMapper
import com.cezarykluczynski.stapi.server.staff.query.StaffSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class StaffSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private StaffSoapQuery staffSoapQueryBuilderMock

	private StaffBaseSoapMapper staffBaseSoapMapperMock

	private StaffFullSoapMapper staffFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private StaffSoapReader staffSoapReader

	void setup() {
		staffSoapQueryBuilderMock = Mock()
		staffBaseSoapMapperMock = Mock()
		staffFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		staffSoapReader = new StaffSoapReader(staffSoapQueryBuilderMock, staffBaseSoapMapperMock, staffFullSoapMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Staff> dbStaffList = Lists.newArrayList()
		Page<Staff> dbStaffPage = Mock()
		List<StaffBase> soapStaffList = Lists.newArrayList(new StaffBase(uid: UID))
		StaffBaseRequest staffBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		StaffBaseResponse staffResponse = staffSoapReader.readBase(staffBaseRequest)

		then:
		1 * staffSoapQueryBuilderMock.query(staffBaseRequest) >> dbStaffPage
		1 * dbStaffPage.content >> dbStaffList
		1 * pageMapperMock.fromPageToSoapResponsePage(dbStaffPage) >> responsePage
		1 * staffBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * staffBaseSoapMapperMock.mapBase(dbStaffList) >> soapStaffList
		0 * _
		staffResponse.staff[0].uid == UID
		staffResponse.page == responsePage
		staffResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		StaffFull staffFull = new StaffFull(uid: UID)
		Staff staff = Mock()
		Page<Staff> staffPage = Mock()
		StaffFullRequest staffFullRequest = new StaffFullRequest(uid: UID)

		when:
		StaffFullResponse staffFullResponse = staffSoapReader.readFull(staffFullRequest)

		then:
		1 * staffSoapQueryBuilderMock.query(staffFullRequest) >> staffPage
		1 * staffPage.content >> Lists.newArrayList(staff)
		1 * staffFullSoapMapperMock.mapFull(staff) >> staffFull
		0 * _
		staffFullResponse.staff.uid == UID
	}

	void "requires UID in full request"() {
		given:
		StaffFullRequest staffFullRequest = Mock()

		when:
		staffSoapReader.readFull(staffFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
