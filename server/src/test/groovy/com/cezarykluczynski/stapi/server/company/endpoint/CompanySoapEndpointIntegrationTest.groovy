package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortClause
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortDirectionEnum
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMPANIES)
})
class CompanySoapEndpointIntegrationTest extends AbstractCompanyEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets company by UID"() {
		when:
		CompanyFullResponse companyFullResponse = stapiSoapClient.companyPortType
				.getCompanyFull(new CompanyFullRequest(uid: 'COMA0000111666'))

		then:
		companyFullResponse.company.name == 'PBS'
	}

	void "gets companies that done digital visual effects, matte painting, and model and miniatures effects, sorted by name descending"() {
		when:
		CompanyBaseResponse astronomicalObjectResponse = stapiSoapClient.companyPortType
				.getCompanyBase(new CompanyBaseRequest(
				digitalVisualEffectsCompany: true,
				mattePaintingCompany: true,
				modelAndMiniatureEffectsCompany: true,
				sort: new RequestSort(
						clauses: Lists.newArrayList(
								new RequestSortClause(
										name: 'name',
										direction: RequestSortDirectionEnum.DESC)
						)
				)))

		then:
		astronomicalObjectResponse.companies.size() == 2
		astronomicalObjectResponse.companies[0].name == 'Industrial Light & Magic'
		astronomicalObjectResponse.companies[1].name == 'Digital Domain'
	}

}
