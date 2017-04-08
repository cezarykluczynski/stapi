package com.cezarykluczynski.stapi.server.company.query

import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CompanyRestQueryTest extends Specification {

	private CompanyBaseRestMapper companyRestMapperMock

	private PageMapper pageMapperMock

	private CompanyRepository companyRepositoryMock

	private CompanyRestQuery companyRestQuery

	void setup() {
		companyRestMapperMock = Mock()
		pageMapperMock = Mock()
		companyRepositoryMock = Mock()
		companyRestQuery = new CompanyRestQuery(companyRestMapperMock, pageMapperMock, companyRepositoryMock)
	}

	void "maps CompanyRestBeanParams to CompanyRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		CompanyRestBeanParams companyRestBeanParams = Mock()
		CompanyRequestDTO companyRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = companyRestQuery.query(companyRestBeanParams)

		then:
		1 * companyRestMapperMock.mapBase(companyRestBeanParams) >> companyRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(companyRestBeanParams) >> pageRequest
		1 * companyRepositoryMock.findMatching(companyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
