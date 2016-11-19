package com.cezarykluczynski.stapi.server.staff.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffRequestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class StaffSoapQueryTest extends Specification {

	private StaffRequestMapper staffRequestMapperMock

	private PageMapper pageMapperMock

	private StaffRepository staffRepositoryMock

	private StaffSoapQuery staffSoapQuery

	def setup() {
		staffRequestMapperMock = Mock(StaffRequestMapper)
		pageMapperMock = Mock(PageMapper)
		staffRepositoryMock = Mock(StaffRepository)
		staffSoapQuery = new StaffSoapQuery(staffRequestMapperMock, pageMapperMock,
				staffRepositoryMock)
	}

	def "maps StaffRequest to StaffRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		StaffRequest staffRequest = Mock(StaffRequest) {
			getPage() >> requestPage
		}
		StaffRequestDTO staffRequestDTO = Mock(StaffRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = staffSoapQuery.query(staffRequest)

		then:
		1 * staffRequestMapperMock.map(staffRequest) >> staffRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * staffRepositoryMock.findMatching(staffRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
