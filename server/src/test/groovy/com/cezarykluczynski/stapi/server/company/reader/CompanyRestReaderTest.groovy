package com.cezarykluczynski.stapi.server.company.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBase as RESTCompany
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFull
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams
import com.cezarykluczynski.stapi.server.company.mapper.CompanyRestMapper
import com.cezarykluczynski.stapi.server.company.query.CompanyRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class CompanyRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private CompanyRestQuery companyRestQueryBuilderMock

	private CompanyRestMapper companyRestMapperMock

	private PageMapper pageMapperMock

	private CompanyRestReader companyRestReader

	void setup() {
		companyRestQueryBuilderMock = Mock(CompanyRestQuery)
		companyRestMapperMock = Mock(CompanyRestMapper)
		pageMapperMock = Mock(PageMapper)
		companyRestReader = new CompanyRestReader(companyRestQueryBuilderMock, companyRestMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into CompanyResponse"() {
		given:
		List<Company> dbCompanyList = Lists.newArrayList()
		Page<Company> dbCompanyPage = Mock(Page)
		dbCompanyPage.content >> dbCompanyList
		List<RESTCompany> soapCompanyList = Lists.newArrayList(new RESTCompany(guid: GUID))
		CompanyRestBeanParams companyRestBeanParams = Mock(CompanyRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		CompanyBaseResponse companyResponse = companyRestReader.readBase(companyRestBeanParams)

		then:
		1 * companyRestQueryBuilderMock.query(companyRestBeanParams) >> dbCompanyPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbCompanyPage) >> responsePage
		1 * companyRestMapperMock.mapBase(dbCompanyList) >> soapCompanyList
		companyResponse.companies[0].guid == GUID
		companyResponse.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		CompanyFull companyFull = Mock(CompanyFull)
		Company company = Mock(Company)
		List<Company> dbCompanyList = Lists.newArrayList(company)
		Page<Company> dbCompanyPage = Mock(Page)

		when:
		CompanyFullResponse companyResponseOutput = companyRestReader.readFull(GUID)

		then:
		1 * companyRestQueryBuilderMock.query(_ as CompanyRestBeanParams) >> { CompanyRestBeanParams companyRestBeanParams ->
			assert companyRestBeanParams.guid == GUID
			dbCompanyPage
		}
		1 * dbCompanyPage.content >> dbCompanyList
		1 * companyRestMapperMock.mapFull(company) >> companyFull
		0 * _
		companyResponseOutput.company == companyFull
	}

}
