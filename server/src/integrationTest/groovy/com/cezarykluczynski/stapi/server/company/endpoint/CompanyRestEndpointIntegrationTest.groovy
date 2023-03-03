package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.api.dto.CompanyV2SearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

import java.util.stream.Collectors

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMPANIES)
})
class CompanyRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets company by UID"() {
		when:
		CompanyV2FullResponse companyV2FullResponse = stapiRestClient.company.getV2('COMA0000006521')

		then:
		companyV2FullResponse.company.name == 'NBC'
	}

	void "gets CBS-related broadcasters"() {
		given:
		CompanyV2SearchCriteria companyV2SearchCriteria = new CompanyV2SearchCriteria(
				name: 'CBS',
				broadcaster: true
		)

		when:
		CompanyV2BaseResponse companyV2BaseResponse = stapiRestClient.company.searchV2(companyV2SearchCriteria)
		List<String> companyNameList = companyV2BaseResponse.companies
				.stream()
				.map { company -> company.name }
				.collect(Collectors.toList())

		then:
		companyNameList.contains 'CBS Action'
		companyNameList.contains 'CBS Broadcasting'
		companyNameList.contains 'CBS Television Network'
	}

}
