package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyResponse
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

	@SuppressWarnings('ClosureAsLastMethodParameter')
	void "gets CBS-related broadcasters"() {
		when:
		CompanyResponse companyResponseResponse = stapiRestClient.companyApi
				.companyPost(0, 20, null, null, 'CBS', true, false, false, false, false, false, false, false, false, false, false, false, false,
				false, false, false, false)
		List<String> companyNameList = companyResponseResponse.companies
				.stream()
				.map({ company -> company.name })
				.collect(Collectors.toList())

		then:
		companyNameList.contains 'CBS Action'
		companyNameList.contains 'CBS Studios'
	}

}
