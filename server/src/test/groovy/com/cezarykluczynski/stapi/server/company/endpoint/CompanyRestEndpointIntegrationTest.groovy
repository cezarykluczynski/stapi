package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

import java.util.stream.Collectors

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMPANIES)
})
class CompanyRestEndpointIntegrationTest extends AbstractCompanyEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets company by UID"() {
		when:
		CompanyFullResponse companyFullResponse = stapiRestClient.companyApi.companyGet('COMA0000006521', null)

		then:
		companyFullResponse.company.name == 'NBC'
	}

	void "gets CBS-related broadcasters"() {
		when:
		CompanyBaseResponse companyResponse = stapiRestClient.companyApi.companySearchPost(0, 20, null, null, 'CBS', true, false, false, false, false,
				false, false, false, false, false, false, false, false, false, false, false, false)
		List<String> companyNameList = companyResponse.companies
				.stream()
				.map { company -> company.name }
				.collect(Collectors.toList())

		then:
		companyNameList.contains 'CBS Action'
		companyNameList.contains 'CBS Studios'
	}

}
