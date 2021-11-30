package com.cezarykluczynski.stapi.server.staff.query

import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams
import com.cezarykluczynski.stapi.server.staff.dto.StaffV2RestBeanParams
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class StaffRestQueryTest extends Specification {

	private StaffBaseRestMapper staffBaseRestMapperMock

	private PageMapper pageMapperMock

	private StaffRepository staffRepositoryMock

	private StaffRestQuery staffRestQuery

	void setup() {
		staffBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		staffRepositoryMock = Mock()
		staffRestQuery = new StaffRestQuery(staffBaseRestMapperMock, pageMapperMock, staffRepositoryMock)
	}

	void "maps StaffRestBeanParams to StaffRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		StaffRestBeanParams staffRestBeanParams = Mock()
		StaffRequestDTO staffRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = staffRestQuery.query(staffRestBeanParams)

		then:
		1 * staffBaseRestMapperMock.mapBase(staffRestBeanParams) >> staffRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(staffRestBeanParams) >> pageRequest
		1 * staffRepositoryMock.findMatching(staffRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps StaffV2RestBeanParams to StaffRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		StaffV2RestBeanParams staffV2RestBeanParams = Mock()
		StaffRequestDTO staffRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = staffRestQuery.query(staffV2RestBeanParams)

		then:
		1 * staffBaseRestMapperMock.mapV2Base(staffV2RestBeanParams) >> staffRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(staffV2RestBeanParams) >> pageRequest
		1 * staffRepositoryMock.findMatching(staffRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
