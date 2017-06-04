package com.cezarykluczynski.stapi.server.staff.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBase
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFull
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFullResponse
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullRestMapper
import com.cezarykluczynski.stapi.server.staff.query.StaffRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class StaffRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private StaffRestQuery staffRestQueryBuilderMock

	private StaffBaseRestMapper staffBaseRestMapperMock

	private StaffFullRestMapper staffFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private StaffRestReader staffRestReader

	void setup() {
		staffRestQueryBuilderMock = Mock()
		staffBaseRestMapperMock = Mock()
		staffFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		staffRestReader = new StaffRestReader(staffRestQueryBuilderMock, staffBaseRestMapperMock, staffFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		StaffBase staffBase = Mock()
		Staff staff = Mock()
		StaffRestBeanParams staffRestBeanParams = Mock()
		List<StaffBase> restStaffList = Lists.newArrayList(staffBase)
		List<Staff> staffList = Lists.newArrayList(staff)
		Page<Staff> staffPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		StaffBaseResponse staffResponseOutput = staffRestReader.readBase(staffRestBeanParams)

		then:
		1 * staffRestQueryBuilderMock.query(staffRestBeanParams) >> staffPage
		1 * pageMapperMock.fromPageToRestResponsePage(staffPage) >> responsePage
		1 * staffRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * staffPage.content >> staffList
		1 * staffBaseRestMapperMock.mapBase(staffList) >> restStaffList
		0 * _
		staffResponseOutput.staff == restStaffList
		staffResponseOutput.page == responsePage
		staffResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		StaffFull staffFull = Mock()
		Staff staff = Mock()
		List<Staff> staffList = Lists.newArrayList(staff)
		Page<Staff> staffPage = Mock()

		when:
		StaffFullResponse staffResponseOutput = staffRestReader.readFull(UID)

		then:
		1 * staffRestQueryBuilderMock.query(_ as StaffRestBeanParams) >> { StaffRestBeanParams staffRestBeanParams ->
			assert staffRestBeanParams.uid == UID
			staffPage
		}
		1 * staffPage.content >> staffList
		1 * staffFullRestMapperMock.mapFull(staff) >> staffFull
		0 * _
		staffResponseOutput.staff == staffFull
	}

	void "requires UID in full request"() {
		when:
		staffRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
