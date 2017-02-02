package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CompanyRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyResponse
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

	void "gets companies that done digital visual effects, matte painting, and model and miniatures effects, sorted by name descending"() {
		when:
		CompanyResponse astronomicalObjectResponse = stapiSoapClient.companyPortType
				.getCompanies(new CompanyRequest(
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
