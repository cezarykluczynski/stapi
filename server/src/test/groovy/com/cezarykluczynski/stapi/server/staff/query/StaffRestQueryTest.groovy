package com.cezarykluczynski.stapi.server.staff.query

import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams
import com.cezarykluczynski.stapi.server.staff.mapper.StaffRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class StaffRestQueryTest extends Specification {

	private StaffRestMapper staffRestMapperMock

	private PageMapper pageMapperMock

	private StaffRepository staffRepositoryMock

	private StaffRestQuery staffRestQuery

	def setup() {
		staffRestMapperMock = Mock(StaffRestMapper)
		pageMapperMock = Mock(PageMapper)
		staffRepositoryMock = Mock(StaffRepository)
		staffRestQuery = new StaffRestQuery(staffRestMapperMock, pageMapperMock,
				staffRepositoryMock)
	}

	def "maps StaffRestBeanParams to StaffRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		StaffRestBeanParams staffRestBeanParams = Mock(StaffRestBeanParams) {

		}
		StaffRequestDTO staffRequestDTO = Mock(StaffRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = staffRestQuery.query(staffRestBeanParams)

		then:
		1 * staffRestMapperMock.map(staffRestBeanParams) >> staffRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(staffRestBeanParams) >> pageRequest
		1 * staffRepositoryMock.findMatching(staffRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
