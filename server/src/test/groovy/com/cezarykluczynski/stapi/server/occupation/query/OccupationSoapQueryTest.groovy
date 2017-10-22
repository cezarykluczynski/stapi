package com.cezarykluczynski.stapi.server.occupation.query

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseSoapMapper
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class OccupationSoapQueryTest extends Specification {

	private OccupationBaseSoapMapper occupationBaseSoapMapperMock

	private OccupationFullSoapMapper occupationFullSoapMapperMock

	private PageMapper pageMapperMock

	private OccupationRepository occupationRepositoryMock

	private OccupationSoapQuery occupationSoapQuery

	void setup() {
		occupationBaseSoapMapperMock = Mock()
		occupationFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		occupationRepositoryMock = Mock()
		occupationSoapQuery = new OccupationSoapQuery(occupationBaseSoapMapperMock, occupationFullSoapMapperMock, pageMapperMock,
				occupationRepositoryMock)
	}

	void "maps OccupationBaseRequest to OccupationRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		OccupationBaseRequest occupationRequest = Mock()
		occupationRequest.page >> requestPage
		OccupationRequestDTO occupationRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = occupationSoapQuery.query(occupationRequest)

		then:
		1 * occupationBaseSoapMapperMock.mapBase(occupationRequest) >> occupationRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * occupationRepositoryMock.findMatching(occupationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps OccupationFullRequest to OccupationRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		OccupationFullRequest occupationRequest = Mock()
		OccupationRequestDTO occupationRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = occupationSoapQuery.query(occupationRequest)

		then:
		1 * occupationFullSoapMapperMock.mapFull(occupationRequest) >> occupationRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * occupationRepositoryMock.findMatching(occupationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
