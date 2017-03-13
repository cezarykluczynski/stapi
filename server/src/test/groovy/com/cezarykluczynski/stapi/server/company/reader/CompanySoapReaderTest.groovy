package com.cezarykluczynski.stapi.server.company.reader

import com.cezarykluczynski.stapi.client.v1.soap.Company as SOAPCompany
import com.cezarykluczynski.stapi.client.v1.soap.CompanyRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.company.entity.Company as DBCompany
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

	void "gets database entities and puts them into CompanyResponse"() {
		given:
		List<DBCompany> dbCompanyList = Lists.newArrayList()
		Page<DBCompany> dbCompanyPage = Mock(Page)
		dbCompanyPage.content >> dbCompanyList
		List<SOAPCompany> soapCompanyList = Lists.newArrayList(new SOAPCompany(guid: GUID))
		CompanyRequest companyRequest = Mock(CompanyRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		CompanyResponse companyResponse = companySoapReader.readBase(companyRequest)

		then:
		1 * companySoapQueryBuilderMock.query(companyRequest) >> dbCompanyPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbCompanyPage) >> responsePage
		1 * companySoapMapperMock.map(dbCompanyList) >> soapCompanyList
		companyResponse.companies[0].guid == GUID
		companyResponse.page == responsePage
	}

}
