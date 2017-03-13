package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CompanyRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyResponse
import com.cezarykluczynski.stapi.server.company.reader.CompanySoapReader
import spock.lang.Specification

class CompanySoapEndpointTest extends Specification {

	private CompanySoapReader companySoapReaderMock

	private CompanySoapEndpoint companySoapEndpoint

	void setup() {
		companySoapReaderMock = Mock(CompanySoapReader)
		companySoapEndpoint = new CompanySoapEndpoint(companySoapReaderMock)
	}

	void "passes call to CompanySoapReader"() {
		given:
		CompanyRequest companyRequest = Mock(CompanyRequest)
		CompanyResponse companyResponse = Mock(CompanyResponse)

		when:
		CompanyResponse companyResponseResult = companySoapEndpoint.getCompanies(companyRequest)

		then:
		1 * companySoapReaderMock.readBase(companyRequest) >> companyResponse
		companyResponseResult == companyResponse
	}

}
