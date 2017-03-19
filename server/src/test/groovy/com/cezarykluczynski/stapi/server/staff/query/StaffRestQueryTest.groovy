package com.cezarykluczynski.stapi.server.staff.query

import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class StaffRestQueryTest extends Specification {

	private StaffBaseRestMapper staffRestMapperMock

	private PageMapper pageMapperMock

	private StaffRepository staffRepositoryMock

	private StaffRestQuery staffRestQuery

	void setup() {
		staffRestMapperMock = Mock(StaffBaseRestMapper)
		pageMapperMock = Mock(PageMapper)
		staffRepositoryMock = Mock(StaffRepository)
		staffRestQuery = new StaffRestQuery(staffRestMapperMock, pageMapperMock,
				staffRepositoryMock)
	}

	void "maps StaffRestBeanParams to StaffRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		StaffRestBeanParams staffRestBeanParams = Mock(StaffRestBeanParams) {

		}
		StaffRequestDTO staffRequestDTO = Mock(StaffRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = staffRestQuery.query(staffRestBeanParams)

		then:
		1 * staffRestMapperMock.mapBase(staffRestBeanParams) >> staffRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(staffRestBeanParams) >> pageRequest
		1 * staffRepositoryMock.findMatching(staffRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
