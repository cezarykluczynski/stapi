package com.cezarykluczynski.stapi.server.staff.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2Base
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2Full
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2FullResponse
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.staff.dto.StaffV2RestBeanParams
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullRestMapper
import com.cezarykluczynski.stapi.server.staff.query.StaffRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class StaffV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private StaffRestQuery staffRestQueryBuilderMock

	private StaffBaseRestMapper staffBaseRestMapperMock

	private StaffFullRestMapper staffFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private StaffV2RestReader staffV2RestReader

	void setup() {
		staffRestQueryBuilderMock = Mock()
		staffBaseRestMapperMock = Mock()
		staffFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		staffV2RestReader = new StaffV2RestReader(staffRestQueryBuilderMock, staffBaseRestMapperMock, staffFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		StaffV2Base staffBase = Mock()
		Staff staff = Mock()
		StaffV2RestBeanParams staffV2RestBeanParams = Mock()
		List<StaffV2Base> restStaffList = Lists.newArrayList(staffBase)
		List<Staff> staffList = Lists.newArrayList(staff)
		Page<Staff> staffPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		StaffV2BaseResponse staffV2ResponseOutput = staffV2RestReader.readBase(staffV2RestBeanParams)

		then:
		1 * staffRestQueryBuilderMock.query(staffV2RestBeanParams) >> staffPage
		1 * pageMapperMock.fromPageToRestResponsePage(staffPage) >> responsePage
		1 * staffV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * staffPage.content >> staffList
		1 * staffBaseRestMapperMock.mapV2Base(staffList) >> restStaffList
		0 * _
		staffV2ResponseOutput.staff == restStaffList
		staffV2ResponseOutput.page == responsePage
		staffV2ResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		StaffV2Full staffV2Full = Mock()
		Staff staff = Mock()
		List<Staff> staffList = Lists.newArrayList(staff)
		Page<Staff> staffPage = Mock()

		when:
		StaffV2FullResponse staffV2ResponseOutput = staffV2RestReader.readFull(UID)

		then:
		1 * staffRestQueryBuilderMock.query(_ as StaffV2RestBeanParams) >> { StaffV2RestBeanParams staffV2RestBeanParams ->
			assert staffV2RestBeanParams.uid == UID
			staffPage
		}
		1 * staffPage.content >> staffList
		1 * staffFullRestMapperMock.mapV2Full(staff) >> staffV2Full
		0 * _
		staffV2ResponseOutput.staff == staffV2Full
	}

	void "requires UID in full request"() {
		when:
		staffV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
