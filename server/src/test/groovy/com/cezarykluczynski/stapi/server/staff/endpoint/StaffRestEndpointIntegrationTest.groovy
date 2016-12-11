package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_STAFF)
})
class StaffRestEndpointIntegrationTest extends AbstractStaffEndpointIntegrationTest {

	def setup() {
		createRestClient()
	}

	def "gets first page of staff"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		StaffResponse staffResponse = stapiRestClient.staffApi.staffGet(pageNumber, pageSize)

		then:
		staffResponse.page.pageNumber == pageNumber
		staffResponse.page.pageSize == pageSize
		staffResponse.staff.size() == pageSize
	}

	def "gets staff by guid"() {
		when:
		StaffResponse staffResponse = stapiRestClient.staffApi.staffPost(null, null, GUID, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null)

		then:
		staffResponse.page.totalElements == 1
		staffResponse.staff[0].guid == GUID
	}

}
