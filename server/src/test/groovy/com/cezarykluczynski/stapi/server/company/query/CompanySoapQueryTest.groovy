package com.cezarykluczynski.stapi.server.company.query

import com.cezarykluczynski.stapi.client.v1.soap.CompanyRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.company.mapper.CompanySoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CompanySoapQueryTest extends Specification {

	private CompanySoapMapper companySoapMapperMock

	private PageMapper pageMapperMock

	private CompanyRepository companyRepositoryMock

	private CompanySoapQuery companySoapQuery

	void setup() {
		companySoapMapperMock = Mock(CompanySoapMapper)
		pageMapperMock = Mock(PageMapper)
		companyRepositoryMock = Mock(CompanyRepository)
		companySoapQuery = new CompanySoapQuery(companySoapMapperMock, pageMapperMock,
				companyRepositoryMock)
	}

	void "maps CompanyRequest to CompanyRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		CompanyRequest companyRequest = Mock(CompanyRequest)
		companyRequest.page >> requestPage
		CompanyRequestDTO companyRequestDTO = Mock(CompanyRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = companySoapQuery.query(companyRequest)

		then:
		1 * companySoapMapperMock.map(companyRequest) >> companyRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * companyRepositoryMock.findMatching(companyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
