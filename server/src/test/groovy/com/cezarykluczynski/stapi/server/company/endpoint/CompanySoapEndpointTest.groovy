package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullResponse
import com.cezarykluczynski.stapi.server.company.reader.CompanySoapReader
import spock.lang.Specification

class CompanySoapEndpointTest extends Specification {

	private CompanySoapReader companySoapReaderMock

	private CompanySoapEndpoint companySoapEndpoint

	void setup() {
		companySoapReaderMock = Mock(CompanySoapReader)
		companySoapEndpoint = new CompanySoapEndpoint(companySoapReaderMock)
	}

	void "passes base call to CompanySoapReader"() {
		given:
		CompanyBaseRequest companyRequest = Mock(CompanyBaseRequest)
		CompanyBaseResponse companyResponse = Mock(CompanyBaseResponse)

		when:
		CompanyBaseResponse companyResponseResult = companySoapEndpoint.getCompanyBase(companyRequest)

		then:
		1 * companySoapReaderMock.readBase(companyRequest) >> companyResponse
		companyResponseResult == companyResponse
	}

	void "passes full call to CompanySoapReader"() {
		given:
		CompanyFullRequest companyFullRequest = Mock(CompanyFullRequest)
		CompanyFullResponse companyFullResponse = Mock(CompanyFullResponse)

		when:
		CompanyFullResponse companyResponseResult = companySoapEndpoint.getCompanyFull(companyFullRequest)

		then:
		1 * companySoapReaderMock.readFull(companyFullRequest) >> companyFullResponse
		companyResponseResult == companyFullResponse
	}

}
