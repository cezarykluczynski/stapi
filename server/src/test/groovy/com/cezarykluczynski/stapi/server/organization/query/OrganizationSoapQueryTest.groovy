package com.cezarykluczynski.stapi.server.organization.query

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class OrganizationSoapQueryTest extends Specification {

	private OrganizationBaseSoapMapper organizationBaseSoapMapperMock

	private OrganizationFullSoapMapper organizationFullSoapMapperMock

	private PageMapper pageMapperMock

	private OrganizationRepository organizationRepositoryMock

	private OrganizationSoapQuery organizationSoapQuery

	void setup() {
		organizationBaseSoapMapperMock = Mock(OrganizationBaseSoapMapper)
		organizationFullSoapMapperMock = Mock(OrganizationFullSoapMapper)
		pageMapperMock = Mock(PageMapper)
		organizationRepositoryMock = Mock(OrganizationRepository)
		organizationSoapQuery = new OrganizationSoapQuery(organizationBaseSoapMapperMock, organizationFullSoapMapperMock, pageMapperMock,
				organizationRepositoryMock)
	}

	void "maps OrganizationBaseRequest to OrganizationRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		OrganizationBaseRequest organizationRequest = Mock(OrganizationBaseRequest)
		organizationRequest.page >> requestPage
		OrganizationRequestDTO organizationRequestDTO = Mock(OrganizationRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = organizationSoapQuery.query(organizationRequest)

		then:
		1 * organizationBaseSoapMapperMock.mapBase(organizationRequest) >> organizationRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * organizationRepositoryMock.findMatching(organizationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps OrganizationFullRequest to OrganizationRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		OrganizationFullRequest organizationRequest = Mock(OrganizationFullRequest)
		OrganizationRequestDTO organizationRequestDTO = Mock(OrganizationRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = organizationSoapQuery.query(organizationRequest)

		then:
		1 * organizationFullSoapMapperMock.mapFull(organizationRequest) >> organizationRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * organizationRepositoryMock.findMatching(organizationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
