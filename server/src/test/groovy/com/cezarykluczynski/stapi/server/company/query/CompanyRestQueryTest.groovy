package com.cezarykluczynski.stapi.server.company.query

import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams
import com.cezarykluczynski.stapi.server.company.dto.CompanyV2RestBeanParams
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CompanyRestQueryTest extends Specification {

	private CompanyBaseRestMapper companyBaseRestMapperMock

	private PageMapper pageMapperMock

	private CompanyRepository companyRepositoryMock

	private CompanyRestQuery companyRestQuery

	void setup() {
		companyBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		companyRepositoryMock = Mock()
		companyRestQuery = new CompanyRestQuery(companyBaseRestMapperMock, pageMapperMock, companyRepositoryMock)
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
		1 * companyBaseRestMapperMock.mapBase(companyRestBeanParams) >> companyRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(companyRestBeanParams) >> pageRequest
		1 * companyRepositoryMock.findMatching(companyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps CompanyV2RestBeanParams to CompanyRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		CompanyV2RestBeanParams companyV2RestBeanParams = Mock()
		CompanyRequestDTO companyRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = companyRestQuery.query(companyV2RestBeanParams)

		then:
		1 * companyBaseRestMapperMock.mapV2Base(companyV2RestBeanParams) >> companyRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(companyV2RestBeanParams) >> pageRequest
		1 * companyRepositoryMock.findMatching(companyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
