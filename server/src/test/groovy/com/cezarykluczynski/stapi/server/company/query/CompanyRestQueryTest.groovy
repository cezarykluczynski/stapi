package com.cezarykluczynski.stapi.server.company.query

import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams
import com.cezarykluczynski.stapi.server.company.mapper.CompanyRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CompanyRestQueryTest extends Specification {

	private CompanyRestMapper companyRestMapperMock

	private PageMapper pageMapperMock

	private CompanyRepository companyRepositoryMock

	private CompanyRestQuery companyRestQuery

	void setup() {
		companyRestMapperMock = Mock(CompanyRestMapper)
		pageMapperMock = Mock(PageMapper)
		companyRepositoryMock = Mock(CompanyRepository)
		companyRestQuery = new CompanyRestQuery(companyRestMapperMock, pageMapperMock, companyRepositoryMock)
	}

	void "maps CompanyRestBeanParams to CompanyRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		CompanyRestBeanParams companyRestBeanParams = Mock(CompanyRestBeanParams) {

		}
		CompanyRequestDTO companyRequestDTO = Mock(CompanyRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = companyRestQuery.query(companyRestBeanParams)

		then:
		1 * companyRestMapperMock.mapBase(companyRestBeanParams) >> companyRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(companyRestBeanParams) >> pageRequest
		1 * companyRepositoryMock.findMatching(companyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
