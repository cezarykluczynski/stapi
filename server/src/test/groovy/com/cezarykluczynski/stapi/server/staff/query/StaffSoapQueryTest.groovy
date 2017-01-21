package com.cezarykluczynski.stapi.server.staff.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class StaffSoapQueryTest extends Specification {

	private StaffSoapMapper staffSoapMapperMock

	private PageMapper pageMapperMock

	private StaffRepository staffRepositoryMock

	private StaffSoapQuery staffSoapQuery

	void setup() {
		staffSoapMapperMock = Mock(StaffSoapMapper)
		pageMapperMock = Mock(PageMapper)
		staffRepositoryMock = Mock(StaffRepository)
		staffSoapQuery = new StaffSoapQuery(staffSoapMapperMock, pageMapperMock,
				staffRepositoryMock)
	}

	void "maps StaffRequest to StaffRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		StaffRequest staffRequest = Mock(StaffRequest)
		staffRequest.page >> requestPage
		StaffRequestDTO staffRequestDTO = Mock(StaffRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = staffSoapQuery.query(staffRequest)

		then:
		1 * staffSoapMapperMock.map(staffRequest) >> staffRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * staffRepositoryMock.findMatching(staffRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
