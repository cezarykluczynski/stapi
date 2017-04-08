package com.cezarykluczynski.stapi.server.company.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBase
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFull
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullRestMapper
import com.cezarykluczynski.stapi.server.company.query.CompanyRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class CompanyRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private CompanyRestQuery companyRestQueryBuilderMock

	private CompanyBaseRestMapper companyBaseRestMapperMock

	private CompanyFullRestMapper companyFullRestMapperMock

	private PageMapper pageMapperMock

	private CompanyRestReader companyRestReader

	void setup() {
		companyRestQueryBuilderMock = Mock()
		companyBaseRestMapperMock = Mock()
		companyFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		companyRestReader = new CompanyRestReader(companyRestQueryBuilderMock, companyBaseRestMapperMock, companyFullRestMapperMock,
				pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		CompanyBase companyBase = Mock()
		Company company = Mock()
		CompanyRestBeanParams companyRestBeanParams = Mock()
		List<CompanyBase> restCompanyList = Lists.newArrayList(companyBase)
		List<Company> companyList = Lists.newArrayList(company)
		Page<Company> companyPage = Mock()
		ResponsePage responsePage = Mock()

		when:
		CompanyBaseResponse companyResponseOutput = companyRestReader.readBase(companyRestBeanParams)

		then:
		1 * companyRestQueryBuilderMock.query(companyRestBeanParams) >> companyPage
		1 * pageMapperMock.fromPageToRestResponsePage(companyPage) >> responsePage
		1 * companyPage.content >> companyList
		1 * companyBaseRestMapperMock.mapBase(companyList) >> restCompanyList
		0 * _
		companyResponseOutput.companies == restCompanyList
		companyResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		CompanyFull companyFull = Mock()
		Company company = Mock()
		List<Company> companyList = Lists.newArrayList(company)
		Page<Company> companyPage = Mock()

		when:
		CompanyFullResponse companyResponseOutput = companyRestReader.readFull(GUID)

		then:
		1 * companyRestQueryBuilderMock.query(_ as CompanyRestBeanParams) >> { CompanyRestBeanParams companyRestBeanParams ->
			assert companyRestBeanParams.guid == GUID
			companyPage
		}
		1 * companyPage.content >> companyList
		1 * companyFullRestMapperMock.mapFull(company) >> companyFull
		0 * _
		companyResponseOutput.company == companyFull
	}

}
