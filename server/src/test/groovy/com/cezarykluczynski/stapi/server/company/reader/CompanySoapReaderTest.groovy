package com.cezarykluczynski.stapi.server.company.reader

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBase
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFull
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.company.mapper.CompanySoapMapper
import com.cezarykluczynski.stapi.server.company.query.CompanySoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class CompanySoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private CompanySoapQuery companySoapQueryBuilderMock

	private CompanySoapMapper companySoapMapperMock

	private PageMapper pageMapperMock

	private CompanySoapReader companySoapReader

	void setup() {
		companySoapQueryBuilderMock = Mock(CompanySoapQuery)
		companySoapMapperMock = Mock(CompanySoapMapper)
		pageMapperMock = Mock(PageMapper)
		companySoapReader = new CompanySoapReader(companySoapQueryBuilderMock, companySoapMapperMock, pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Company> dbCompanyList = Lists.newArrayList()
		Page<Company> dbCompanyPage = Mock(Page)
		List<CompanyBase> soapCompanyList = Lists.newArrayList(new CompanyBase(guid: GUID))
		CompanyBaseRequest companyBaseRequest = Mock(CompanyBaseRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		CompanyBaseResponse companyResponse = companySoapReader.readBase(companyBaseRequest)

		then:
		1 * companySoapQueryBuilderMock.query(companyBaseRequest) >> dbCompanyPage
		1 * dbCompanyPage.content >> dbCompanyList
		1 * pageMapperMock.fromPageToSoapResponsePage(dbCompanyPage) >> responsePage
		1 * companySoapMapperMock.mapBase(dbCompanyList) >> soapCompanyList
		companyResponse.companies[0].guid == GUID
		companyResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		CompanyFull companyFull = new CompanyFull(guid: GUID)
		Company company = Mock(Company)
		Page<Company> companyPage = Mock(Page)
		CompanyFullRequest companyFullRequest = Mock(CompanyFullRequest)

		when:
		CompanyFullResponse companyFullResponse = companySoapReader.readFull(companyFullRequest)

		then:
		1 * companySoapQueryBuilderMock.query(companyFullRequest) >> companyPage
		1 * companyPage.content >> Lists.newArrayList(company)
		1 * companySoapMapperMock.mapFull(company) >> companyFull
		companyFullResponse.company.guid == GUID
	}

}
