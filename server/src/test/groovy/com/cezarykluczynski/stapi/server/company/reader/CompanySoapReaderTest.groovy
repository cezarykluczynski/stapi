package com.cezarykluczynski.stapi.server.company.reader

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBase
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFull
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullSoapMapper
import com.cezarykluczynski.stapi.server.company.query.CompanySoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class CompanySoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private CompanySoapQuery companySoapQueryBuilderMock

	private CompanyBaseSoapMapper companyBaseSoapMapperMock

	private CompanyFullSoapMapper companyFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private CompanySoapReader companySoapReader

	void setup() {
		companySoapQueryBuilderMock = Mock()
		companyBaseSoapMapperMock = Mock()
		companyFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		companySoapReader = new CompanySoapReader(companySoapQueryBuilderMock, companyBaseSoapMapperMock, companyFullSoapMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Company> companyList = Lists.newArrayList()
		Page<Company> companyPage = Mock()
		List<CompanyBase> soapCompanyList = Lists.newArrayList(new CompanyBase(uid: UID))
		CompanyBaseRequest companyBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		CompanyBaseResponse companyResponse = companySoapReader.readBase(companyBaseRequest)

		then:
		1 * companySoapQueryBuilderMock.query(companyBaseRequest) >> companyPage
		1 * companyPage.content >> companyList
		1 * pageMapperMock.fromPageToSoapResponsePage(companyPage) >> responsePage
		1 * companyBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * companyBaseSoapMapperMock.mapBase(companyList) >> soapCompanyList
		0 * _
		companyResponse.companies[0].uid == UID
		companyResponse.page == responsePage
		companyResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		CompanyFull companyFull = new CompanyFull(uid: UID)
		Company company = Mock()
		Page<Company> companyPage = Mock()
		CompanyFullRequest companyFullRequest = new CompanyFullRequest(uid: UID)

		when:
		CompanyFullResponse companyFullResponse = companySoapReader.readFull(companyFullRequest)

		then:
		1 * companySoapQueryBuilderMock.query(companyFullRequest) >> companyPage
		1 * companyPage.content >> Lists.newArrayList(company)
		1 * companyFullSoapMapperMock.mapFull(company) >> companyFull
		0 * _
		companyFullResponse.company.uid == UID
	}

	void "requires UID in full request"() {
		given:
		CompanyFullRequest companyFullRequest = Mock()

		when:
		companySoapReader.readFull(companyFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
