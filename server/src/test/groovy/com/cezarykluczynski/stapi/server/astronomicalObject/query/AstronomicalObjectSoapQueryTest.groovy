package com.cezarykluczynski.stapi.server.astronomicalObject.query

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomicalObject.repository.AstronomicalObjectRepository
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseSoapMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class AstronomicalObjectSoapQueryTest extends Specification {

	private AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapperMock

	private AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapperMock

	private PageMapper pageMapperMock

	private AstronomicalObjectRepository astronomicalObjectRepositoryMock

	private AstronomicalObjectSoapQuery astronomicalObjectSoapQuery

	void setup() {
		astronomicalObjectBaseSoapMapperMock = Mock(AstronomicalObjectBaseSoapMapper)
		astronomicalObjectFullSoapMapperMock = Mock(AstronomicalObjectFullSoapMapper)
		pageMapperMock = Mock(PageMapper)
		astronomicalObjectRepositoryMock = Mock(AstronomicalObjectRepository)
		astronomicalObjectSoapQuery = new AstronomicalObjectSoapQuery(astronomicalObjectBaseSoapMapperMock, astronomicalObjectFullSoapMapperMock,
				pageMapperMock, astronomicalObjectRepositoryMock)
	}

	void "maps AstronomicalObjectBaseRequest to AstronomicalObjectRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		AstronomicalObjectBaseRequest astronomicalObjectRequest = Mock(AstronomicalObjectBaseRequest)
		astronomicalObjectRequest.page >> requestPage
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = Mock(AstronomicalObjectRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = astronomicalObjectSoapQuery.query(astronomicalObjectRequest)

		then:
		1 * astronomicalObjectBaseSoapMapperMock.mapBase(astronomicalObjectRequest) >> astronomicalObjectRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * astronomicalObjectRepositoryMock.findMatching(astronomicalObjectRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps AstronomicalObjectFullRequest to AstronomicalObjectRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		AstronomicalObjectFullRequest astronomicalObjectRequest = Mock(AstronomicalObjectFullRequest)
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = Mock(AstronomicalObjectRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = astronomicalObjectSoapQuery.query(astronomicalObjectRequest)

		then:
		1 * astronomicalObjectFullSoapMapperMock.mapFull(astronomicalObjectRequest) >> astronomicalObjectRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * astronomicalObjectRepositoryMock.findMatching(astronomicalObjectRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
