package com.cezarykluczynski.stapi.server.company.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.Company as RESTCompany
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyResponse
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
		CompanyRestBeanParams seriesRestBeanParams = Mock(CompanyRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		CompanyResponse companyResponse = companyRestReader.readBase(seriesRestBeanParams)

		then:
		1 * companyRestQueryBuilderMock.query(seriesRestBeanParams) >> dbCompanyPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbCompanyPage) >> responsePage
		1 * companyRestMapperMock.map(dbCompanyList) >> soapCompanyList
		companyResponse.companies[0].guid == GUID
		companyResponse.page == responsePage
	}

}
