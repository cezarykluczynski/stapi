package com.cezarykluczynski.stapi.server.company.query

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CompanySoapQueryTest extends Specification {

	private CompanyBaseSoapMapper companyBaseSoapMapperMock

	private CompanyFullSoapMapper companyFullSoapMapperMock

	private PageMapper pageMapperMock

	private CompanyRepository companyRepositoryMock

	private CompanySoapQuery companySoapQuery

	void setup() {
		companyBaseSoapMapperMock = Mock()
		companyFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		companyRepositoryMock = Mock()
		companySoapQuery = new CompanySoapQuery(companyBaseSoapMapperMock, companyFullSoapMapperMock, pageMapperMock, companyRepositoryMock)
	}

	void "maps CompanyBaseRequest to CompanyRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		CompanyBaseRequest companyRequest = Mock()
		companyRequest.page >> requestPage
		CompanyRequestDTO companyRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = companySoapQuery.query(companyRequest)

		then:
		1 * companyBaseSoapMapperMock.mapBase(companyRequest) >> companyRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * companyRepositoryMock.findMatching(companyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps CompanyFullRequest to CompanyRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		CompanyFullRequest companyRequest = Mock()
		CompanyRequestDTO companyRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = companySoapQuery.query(companyRequest)

		then:
		1 * companyFullSoapMapperMock.mapFull(companyRequest) >> companyRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * companyRepositoryMock.findMatching(companyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
