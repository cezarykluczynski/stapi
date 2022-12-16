package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.CompanyPortType
import spock.lang.Specification

class CompanyTest extends Specification {

	private CompanyPortType companyPortTypeMock

	private Company company

	void setup() {
		companyPortTypeMock = Mock()
		company = new Company(companyPortTypeMock)
	}

	void "gets single entity"() {
		given:
		CompanyBaseRequest companyBaseRequest = Mock()
		CompanyBaseResponse companyBaseResponse = Mock()

		when:
		CompanyBaseResponse companyBaseResponseOutput = company.search(companyBaseRequest)

		then:
		1 * companyPortTypeMock.getCompanyBase(companyBaseRequest) >> companyBaseResponse
		0 * _
		companyBaseResponse == companyBaseResponseOutput
	}

	void "searches entities"() {
		given:
		CompanyFullRequest companyFullRequest = Mock()
		CompanyFullResponse companyFullResponse = Mock()

		when:
		CompanyFullResponse companyFullResponseOutput = company.get(companyFullRequest)

		then:
		1 * companyPortTypeMock.getCompanyFull(companyFullRequest) >> companyFullResponse
		0 * _
		companyFullResponse == companyFullResponseOutput
	}

}
