package com.cezarykluczynski.stapi.server.astronomicalObject.query

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomicalObject.repository.AstronomicalObjectRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class AstronomicalObjectSoapQueryTest extends Specification {

	private AstronomicalObjectSoapMapper astronomicalObjectSoapMapperMock

	private PageMapper pageMapperMock

	private AstronomicalObjectRepository astronomicalObjectRepositoryMock

	private AstronomicalObjectSoapQuery astronomicalObjectSoapQuery

	void setup() {
		astronomicalObjectSoapMapperMock = Mock(AstronomicalObjectSoapMapper)
		pageMapperMock = Mock(PageMapper)
		astronomicalObjectRepositoryMock = Mock(AstronomicalObjectRepository)
		astronomicalObjectSoapQuery = new AstronomicalObjectSoapQuery(astronomicalObjectSoapMapperMock, pageMapperMock,
				astronomicalObjectRepositoryMock)
	}

	void "maps AstronomicalObjectRequest to AstronomicalObjectRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		AstronomicalObjectRequest astronomicalObjectRequest = Mock(AstronomicalObjectRequest)
		astronomicalObjectRequest.page >> requestPage
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = Mock(AstronomicalObjectRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = astronomicalObjectSoapQuery.query(astronomicalObjectRequest)

		then:
		1 * astronomicalObjectSoapMapperMock.map(astronomicalObjectRequest) >> astronomicalObjectRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * astronomicalObjectRepositoryMock.findMatching(astronomicalObjectRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
